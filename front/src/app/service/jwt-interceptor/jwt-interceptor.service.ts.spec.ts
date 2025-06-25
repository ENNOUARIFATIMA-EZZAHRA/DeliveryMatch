import { TestBed } from '@angular/core/testing';

import { JwtInterceptorServiceTs } from './jwt-interceptor.service.ts';

describe('JwtInterceptorServiceTs', () => {
  let service: JwtInterceptorServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtInterceptorServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
