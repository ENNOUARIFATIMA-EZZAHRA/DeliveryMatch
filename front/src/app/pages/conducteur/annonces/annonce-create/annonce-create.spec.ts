import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceCreate } from './annonce-create';

describe('AnnonceCreate', () => {
  let component: AnnonceCreate;
  let fixture: ComponentFixture<AnnonceCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnonceCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceCreate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
