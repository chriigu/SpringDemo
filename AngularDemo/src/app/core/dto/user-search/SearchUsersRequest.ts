import {OrderDirectionEnum, UserSearchOrderEnum} from '../../../generated/core/api/v1';

export interface SearchUsersRequestDto {

  orderDirection: OrderDirectionEnum;
  order: UserSearchOrderEnum;
  pageNumber: number;
  pageSize: number;
  firstName: string;
  lastName: string;
  email: string;
  birthdate: string;
}
