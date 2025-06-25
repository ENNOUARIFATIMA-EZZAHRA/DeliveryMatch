import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnoncesList } from './annonces-list';

describe('AnnoncesList', () => {
  let component: AnnoncesList;
  let fixture: ComponentFixture<AnnoncesList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnoncesList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnoncesList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
