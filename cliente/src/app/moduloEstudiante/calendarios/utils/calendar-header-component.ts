import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CalendarView } from 'angular-calendar';

@Component({
  selector: 'mwl-demo-utils-calendar-header',
  template: `
    <div class="row text-center">
      <div class="col-md-4">
        <div class="btn-group">
          <div
            class="btn btn-primary"
            mwlCalendarPreviousView
            [view]="view"
            [(viewDate)]="viewDate"
            (viewDateChange)="viewDateChange.next(viewDate)"
          >
            <
          </div>
          <div
            class="btn btn-outline-secondary"
            mwlCalendarToday
            [(viewDate)]="viewDate"
            (viewDateChange)="viewDateChange.next(viewDate)"
          >
            Actual
          </div>
          <div
            class="btn btn-primary"
            mwlCalendarNextView
            [view]="view"
            [(viewDate)]="viewDate"
            (viewDateChange)="viewDateChange.next(viewDate)"
          >
            >
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <h3>{{ viewDate | calendarDate: view + 'ViewTitle':locale }}</h3>
      </div>
      <div class="col-md-4">
        <div class="btn-group">
          <div
            class="btn btn-primary"
            (click)="viewChange.emit(CalendarView.Month)"
            [class.active]="view === CalendarView.Month"
          >
            Mes
          </div>
          <div
            class="btn btn-primary"
            (click)="viewChange.emit(CalendarView.Week)"
            [class.active]="view === CalendarView.Week"
          >
            Semana
          </div>
          <div
            class="btn btn-primary"
            (click)="viewChange.emit(CalendarView.Day)"
            [class.active]="view === CalendarView.Day"
          >
            Dia
          </div>
        </div>
      </div>
    </div>
    <br />
  `,
})
export class CalendarHeaderComponent {
  @Input() view: CalendarView;

  @Input() viewDate: Date;

  @Input() locale: string = 'en';

  @Output() viewChange = new EventEmitter<CalendarView>();

  @Output() viewDateChange = new EventEmitter<Date>();

  CalendarView = CalendarView;
}
