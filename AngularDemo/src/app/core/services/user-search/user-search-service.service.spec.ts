import { TestBed } from '@angular/core/testing';

import { UserSearchService } from './user-search.service';

describe('UserSearchServiceService', () => {
  let service: UserSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
