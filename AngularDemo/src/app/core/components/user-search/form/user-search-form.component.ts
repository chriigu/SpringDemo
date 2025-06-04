import {ChangeDetectionStrategy, Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule, MatLabel} from '@angular/material/form-field';
import {MatHint, MatInputModule} from '@angular/material/input';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {MatFabButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatMomentDateModule} from '@angular/material-moment-adapter';
import moment from 'moment';
import {DateAdapter} from '@angular/material/core';
import {UserSearchService} from '../../../services/user-search/user-search.service';
import {SearchUsersRequestDto} from '../../../dto/user-search/SearchUsersRequest';
import {OrderDirectionEnum, UserSearchOrderEnum} from '../../../../generated/core/api/v1';
import {last} from 'rxjs';


@Component({
  selector: 'app-user-search-form',
  providers: [],
  imports: [
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatLabel,
    MatGridList,
    MatGridTile,
    MatFabButton,
    MatIcon,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatHint
  ],
  templateUrl: './user-search-form.component.html',
  standalone: true,
  styleUrl: './user-search-form.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserSearchFormComponent {
  userFirstName = '';
  userLastName = '';
  userEmail = '';
  userBirthdate: moment.Moment | null = null;

  areAllFieldsEmpty(): boolean {
    return !this.userFirstName && !this.userLastName && !this.userEmail && !this.userBirthdate;
  }

  ngOnInit() {
    console.log('DateAdapter:', this.adapter.constructor.name);
    console.log('Moment locale:', moment.locale()); // should log 'de-ch'
  }

  constructor(private readonly adapter: DateAdapter<any>, private readonly userSearchService: UserSearchService) {
  }

  submitUserSearch() {
    console.log("Search user");

    let request: Partial<SearchUsersRequestDto> = {
      firstName: this.userFirstName,
      lastName: this.userLastName,
      email: this.userEmail,
      birthdate: this.userBirthdate?.toISOString().slice(0,10) ?? ''
    }

    this.userSearchService.updateFilters(request);
  }

  clearSearchResult() {
    this.userSearchService.clearSearchResult();
    this.userFirstName = '';
    this.userLastName = '';
    this.userEmail = '';
    this.userBirthdate = null;
  }

  searchResultEmpty(): boolean {
    return this.userSearchService.searchResultsEmpty();
  }

  ngOnDestroy() {
    this.clearSearchResult();
  }
}
