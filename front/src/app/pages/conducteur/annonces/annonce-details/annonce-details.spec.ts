import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceDetails } from './annonce-details';

describe('AnnonceDetails', () => {
  let component: AnnonceDetails;
  let fixture: ComponentFixture<AnnonceDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnonceDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
