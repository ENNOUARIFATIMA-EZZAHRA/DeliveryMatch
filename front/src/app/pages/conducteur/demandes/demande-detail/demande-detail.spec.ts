import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeDetail } from './demande-detail';

describe('DemandeDetail', () => {
  let component: DemandeDetail;
  let fixture: ComponentFixture<DemandeDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandeDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemandeDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
