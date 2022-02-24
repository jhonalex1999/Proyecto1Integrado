import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarMovParabolicoComponent } from './calendar-mov-parabolico.component';

describe('CalendarMovParabolicoComponent', () => {
  let component: CalendarMovParabolicoComponent;
  let fixture: ComponentFixture<CalendarMovParabolicoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarMovParabolicoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarMovParabolicoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
