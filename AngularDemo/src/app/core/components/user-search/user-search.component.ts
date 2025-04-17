import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';


@Component({
  selector: 'app-user-search',
  imports: [
    MatFormField,
    FormsModule,
    MatInput,
    MatLabel
  ],
  templateUrl: './user-search.component.html',
  standalone: true,
  styleUrl: './user-search.component.css'
})
export class UserSearchComponent {
  userFirstName = '';
  userLastName = '';
  userEmail = '';
  userBirthdate = '';

  areAllFieldsEmpty(): boolean {
    return !this.userFirstName && !this.userLastName && !this.userEmail && !this.userBirthdate;
  }
}
