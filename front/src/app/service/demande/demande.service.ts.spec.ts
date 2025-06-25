import { TestBed } from '@angular/core/testing';

import { DemandeServiceTs } from './demande.service.ts';

describe('DemandeServiceTs', () => {
  let service: DemandeServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
