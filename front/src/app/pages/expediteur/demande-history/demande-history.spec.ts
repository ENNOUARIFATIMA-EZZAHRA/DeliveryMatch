import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeHistory } from './demande-history';

describe('DemandeHistory', () => {
  let component: DemandeHistory;
  let fixture: ComponentFixture<DemandeHistory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandeHistory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemandeHistory);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
