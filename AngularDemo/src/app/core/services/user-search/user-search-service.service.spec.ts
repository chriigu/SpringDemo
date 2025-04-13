import { TestBed } from '@angular/core/testing';

import { UserSearchServiceService } from './user-search-service.service';

describe('UserSearchServiceService', () => {
  let service: UserSearchServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSearchServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
