import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarLeyHookeComponent } from './calendar-ley-hooke.component';

describe('CalendarLeyHookeComponent', () => {
  let component: CalendarLeyHookeComponent;
  let fixture: ComponentFixture<CalendarLeyHookeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarLeyHookeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarLeyHookeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
