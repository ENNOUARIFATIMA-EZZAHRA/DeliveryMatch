import { TestBed } from '@angular/core/testing';

import { AnnonceServiceTs } from './annonce.service.ts';

describe('AnnonceServiceTs', () => {
  let service: AnnonceServiceTs;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnnonceServiceTs);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
