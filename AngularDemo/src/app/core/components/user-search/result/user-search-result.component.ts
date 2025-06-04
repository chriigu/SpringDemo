import {Component, ViewChild} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {MatGridList, MatGridTile, MatGridTileText} from '@angular/material/grid-list';
import {MatSort, MatSortHeader} from '@angular/material/sort';
import {OrderDirectionEnum, UserDto, UserSearchOrderEnum} from '../../../../generated/core/api/v1';
import {UserSearchService} from '../../../services/user-search/user-search.service';
import {Subject, takeUntil} from 'rxjs';
import {MatPaginator} from '@angular/material/paginator';
import { DatePipe } from '@angular/common';
import {formatDate} from '@angular/common';


export interface UserSearchResultEntry {
  firstName: string;
  lastName: string;
  email: string;
  uuid: string;
}

@Component({
  selector: 'app-user-search-result',
  imports: [
    MatCell,
    MatColumnDef,
    MatGridList,
    MatGridTile,
    MatGridTileText,
    MatHeaderCell,
    MatSort,
    MatSortHeader,
    MatPaginator,
    MatTable,
    MatRow,
    MatHeaderRow,
    MatRowDef,
    MatHeaderRowDef,
    MatHeaderCellDef,
    MatCellDef,
    DatePipe
  ],
  templateUrl: './user-search-result.component.html',
  standalone: true,
  styleUrl: './user-search-result.component.css'
})
export class UserSearchResultComponent {
  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'birthdate'];
  dataSource = new MatTableDataSource<UserDto>();
  totalResults = 0;

  private readonly destroy$ = new Subject<void>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  // ngAfterViewInit() {
  //   this.dataSource.sort = this.sort;
  // }


  constructor(private userSearchService: UserSearchService) {
  }

  // ngOnInit() {
  //   console.log("subscribe");
  //   this.userSearchService.searchResults$
  //     .pipe(takeUntil(this.destroy$))
  //     .subscribe(data => {
  //       console.log('got data', data);
  //       this.dataSource.data = data.resultList;
  //       this.dataSource.paginator?.pageIndex = data.pageNumber;
  //       this.dataSource.paginator?.pageSize = data.pageSize;
  //     });
  // }

  ngAfterViewInit(): void {
    this.sort.sortChange
      .pipe(takeUntil(this.destroy$))
      .subscribe(sort => {
        const sortMap: Record<string, UserSearchOrderEnum> = {
          firstName: UserSearchOrderEnum.FirstName,
          lastName: UserSearchOrderEnum.LastName,
          email: UserSearchOrderEnum.Email,
          birthdate: UserSearchOrderEnum.Birthdate
        };
        const order = sortMap[sort.active] ?? UserSearchOrderEnum.FirstName;
        const orderDirection = sort.direction === 'asc' ? OrderDirectionEnum.Asc : OrderDirectionEnum.Desc;

        this.userSearchService.updateSorting(order, orderDirection);
      });

    this.paginator.page
      .pipe(takeUntil(this.destroy$))
      .subscribe(page => {
        this.userSearchService.updatePaging(page.pageIndex, page.pageSize); // paginator starts with 0 whilst SpringBoot backend uses 1 as first page
      });

    this.userSearchService.searchResults$
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => {
        this.dataSource.data = data.resultList;
        this.totalResults = data.totalResults;
        this.paginator.pageIndex = data.pageNumber;
        this.paginator.pageSize = data.pageSize; // paginator starts with 0 whilst SpringBoot backend uses 1 as first page
      });
  }


  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  protected readonly formatDate = formatDate;
}
