import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovParabolicoComponent } from './mov-parabolico.component';

describe('MovParabolicoComponent', () => {
  let component: MovParabolicoComponent;
  let fixture: ComponentFixture<MovParabolicoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovParabolicoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovParabolicoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
