import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AnnoncesManagementComponent } from './annonces-management';


describe('AnnoncesManagement', () => {
  let component: AnnoncesManagementComponent;
  let fixture: ComponentFixture<AnnoncesManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnoncesManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnoncesManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
