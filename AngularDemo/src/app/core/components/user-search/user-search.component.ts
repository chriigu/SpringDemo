import { Component } from '@angular/core';
import {SideNavBarComponent} from '../sidenav/side-nav-bar/side-nav-bar.component';


@Component({
  selector: 'app-user-search',
  imports: [
    SideNavBarComponent
  ],
  templateUrl: './user-search.component.html',
  standalone: true,
  styleUrl: './user-search.component.css'
})
export class UserSearchComponent {

}
