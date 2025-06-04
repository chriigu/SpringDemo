import {inject, Injectable} from '@angular/core';
import {
  OrderDirectionEnum,
  UserControllerService,
  UserSearchOrderEnum,
  UserSearchResult
} from '../../../generated/core/api/v1';
import {BehaviorSubject} from 'rxjs';
import {SearchUsersRequestDto} from '../../dto/user-search/SearchUsersRequest';

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {

  userControllerService = inject(UserControllerService);

  private readonly userSearchRequestSubject = new BehaviorSubject<SearchUsersRequestDto>({
    firstName: "",
    lastName: "",
    email: "",
    birthdate: "",
    order: UserSearchOrderEnum.FirstName,
    orderDirection: OrderDirectionEnum.Asc,
    pageNumber: 0,
    pageSize: 10
  })
  private readonly userSearchResultSubject = new BehaviorSubject<UserSearchResult>({
    resultList: [],
    totalPages: 1,
    totalResults: 0,
    pageNumber: 0,
    pageSize: 10
  });
  searchResults$ = this.userSearchResultSubject.asObservable();

  constructor() {
  }

  updateFilters(filters: Partial<SearchUsersRequestDto>) {
    const current = this.userSearchRequestSubject.value;
    this.userSearchRequestSubject.next({...current, ...filters})
    this.searchUsers();
  }

  updateSorting(order: UserSearchOrderEnum, orderDirection: OrderDirectionEnum) {
    this.updateFilters({order, orderDirection})
  }

  updatePaging(pageNumber: number, pageSize: number) {
    this.updateFilters({pageSize, pageNumber});
  }

  private searchUsers(): void {
    const current = this.userSearchRequestSubject.value;
    this.userControllerService.findUsers(
      current.orderDirection,
      current.order,
      current.pageNumber,
      current.pageSize,
      current.firstName,
      current.lastName,
      current.email,
      current.birthdate
    ).subscribe({
      next:
        result => {
          this.userSearchResultSubject.next(result);
        },
      error: err => {
        console.log("Error searching for users:", err);
        this.clearSearchResult();
      },
    });
  }

  searchResultsEmpty(): boolean {
    return this.userSearchResultSubject.value.totalResults === 0;
  }

  clearSearchResult() {
    this.userSearchResultSubject.next({
      resultList: [],
      totalPages: 1,
      totalResults: 0,
      pageNumber: 0,
      pageSize: 10
    });
  }
}
