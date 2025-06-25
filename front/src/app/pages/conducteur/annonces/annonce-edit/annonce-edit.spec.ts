import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceEdit } from './annonce-edit';

describe('AnnonceEdit', () => {
  let component: AnnonceEdit;
  let fixture: ComponentFixture<AnnonceEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnonceEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceEdit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
