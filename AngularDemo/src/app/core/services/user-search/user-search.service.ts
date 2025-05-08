import {inject, Injectable} from '@angular/core';
import {UserControllerService, UserDto} from '../../../generated/core/api/v1';
import {BehaviorSubject, map, Observable, of, toArray} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {

  userControllerService = inject(UserControllerService);

  private userSearchResultSubject = new BehaviorSubject<UserDto[]>([]);

  searchResults$ = this.userSearchResultSubject.asObservable();

  searchResult: UserDto[] = [{
    firstName: 'fn',
    lastName: 'ln',
    email: 'email',
    uuid: 'uuid'
  }, {
    firstName: 'fn2',
    lastName: 'ln2',
    email: 'email2',
    uuid: 'uuid2'
  }];

  constructor() { }

  searchUsers() {
    this.callSearchUsersApi().subscribe(results =>
    {this.userSearchResultSubject.next(results)})
  }

  clearSearchResult() {
    this.userSearchResultSubject.next([]);
  }

  private callSearchUsersApi() : Observable<UserDto[]> {
    const result$ = of(this.searchResult);

    of(this.searchResult).subscribe(resultArray => {
      console.log("Result as JSON string:", JSON.stringify(resultArray));
    });

    return result$;
  }

  searchResultsEmpty() : boolean {
    return this.userSearchResultSubject.value.length === 0;
  }
}
