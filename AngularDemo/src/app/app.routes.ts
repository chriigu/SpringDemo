import { Routes } from '@angular/router';
import {HomeComponent} from './core/components/home/home.component';
import {UserComponent} from './core/components/user/user.component';
import {UserSearchFormComponent} from './core/components/user-search/form/user-search-form.component';
import {AccountSearchComponent} from './core/components/account-search/account-search.component';
import {UserSearchComponent} from './core/components/user-search/user-search.component';

export const AppRoutes = {
    HOME: '',
    USER: 'user',
    USERS: 'users',
    ACCOUNTS: 'accounts'
}

export const routes: Routes = [
  {
    title: 'Angular Demo Project',
    path: AppRoutes.HOME,
    component: HomeComponent
  },
  {
    title: 'User Management',
    path: AppRoutes.USER,
    component: UserComponent
  },
  {
    title: 'Search Users',
    path: AppRoutes.USERS,
    component: UserSearchComponent
  },
  {
    title: 'Search Accounts',
    path: AppRoutes.ACCOUNTS,
    component: AccountSearchComponent
  }
];
