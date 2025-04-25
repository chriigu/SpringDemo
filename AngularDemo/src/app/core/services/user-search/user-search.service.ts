import {inject, Injectable} from '@angular/core';
import {UserControllerService, UserDto} from '../../../generated/core/api/v1';
import {Observable} from 'rxjs';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {

  userControllerService = inject(UserControllerService);

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

  searchUsers() : Observable<UserDto[]> {
    console.log("return obs ", of(this.searchResult));
    return of(this.searchResult);
  }
}
