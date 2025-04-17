import { Routes } from '@angular/router';
import {HomeComponent} from './core/components/home/home.component';
import {UserComponent} from './core/components/user/user.component';
import {UserSearchComponent} from './core/components/user-search/user-search.component';
import {AccountSearchComponent} from './core/components/accounts/account-search/account-search.component';

export const routes: Routes = [
  {
    title: 'Angular Demo Project',
    path: '',
    component: HomeComponent
  },
  {
    title: 'User Management',
    path: 'user',
    component: UserComponent
  },
  {
    title: 'Search Users',
    path: 'users',
    component: UserSearchComponent
  },
  {
    title: 'Search Accounts',
    path: 'accounts',
    component: AccountSearchComponent
  }
];
