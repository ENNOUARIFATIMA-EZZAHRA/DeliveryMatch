import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceList } from './annonce-list';

describe('AnnonceList', () => {
  let component: AnnonceList;
  let fixture: ComponentFixture<AnnonceList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnonceList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
