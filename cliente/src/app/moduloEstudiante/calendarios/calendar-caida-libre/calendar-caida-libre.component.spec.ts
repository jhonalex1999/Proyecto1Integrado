import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarCaidaLibreComponent } from './calendar-caida-libre.component';

describe('CalendarCaidaLibreComponent', () => {
  let component: CalendarCaidaLibreComponent;
  let fixture: ComponentFixture<CalendarCaidaLibreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarCaidaLibreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarCaidaLibreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
