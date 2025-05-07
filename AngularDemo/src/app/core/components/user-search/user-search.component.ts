import {Component} from '@angular/core';
import {UserSearchFormComponent} from './form/user-search-form.component';
import {UserSearchResultComponent} from './result/user-search-result.component';

@Component({
  selector: 'app-user-search',
  imports: [
    UserSearchFormComponent,
    UserSearchResultComponent
  ],
  templateUrl: './user-search.component.html',
  standalone: true,
  styleUrl: './user-search.component.css'
})
export class UserSearchComponent {

}
