import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandesManagement } from './demandes-management';

describe('DemandesManagement', () => {
  let component: DemandesManagement;
  let fixture: ComponentFixture<DemandesManagement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandesManagement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemandesManagement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
