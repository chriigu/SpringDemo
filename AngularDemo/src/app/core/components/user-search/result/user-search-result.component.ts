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
import {UserDto} from '../../../../generated/core/api/v1';
import {UserSearchService} from '../../../services/user-search/user-search.service';
import {Subject, takeUntil} from 'rxjs';

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
    MatTable,
    MatRow,
    MatHeaderRow,
    MatRowDef,
    MatHeaderRowDef,
    MatHeaderCellDef,
    MatCellDef
  ],
  templateUrl: './user-search-result.component.html',
  standalone: true,
  styleUrl: './user-search-result.component.css'
})
export class UserSearchResultComponent {
  private destroy$ = new Subject<void>();

  dataSource = new MatTableDataSource<UserDto>();
  displayedColumns: string[] = ['firstName', 'lastName', 'email'];

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }


  constructor(private userSearchService : UserSearchService) {
  }

  ngOnInit() {
    console.log("subscribe");
    this.userSearchService.searchResults$
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => {
        console.log('got data', data);
        this.dataSource.data = data;
      });
  }



  ngOnDestroy()
  {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
