import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NouvelleCommandeComponent } from './nouvelle-commande.component';

describe('NouvelleCommandeComponent', () => {
  let component: NouvelleCommandeComponent;
  let fixture: ComponentFixture<NouvelleCommandeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NouvelleCommandeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NouvelleCommandeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
