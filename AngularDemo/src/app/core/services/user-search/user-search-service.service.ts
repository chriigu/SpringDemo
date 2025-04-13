import {inject, Injectable} from '@angular/core';
import {UserControllerService} from '../../../generated/core/api/v1';

@Injectable({
  providedIn: 'root'
})
export class UserSearchServiceService {

  userControllerService = inject(UserControllerService);

  constructor() { }
}
