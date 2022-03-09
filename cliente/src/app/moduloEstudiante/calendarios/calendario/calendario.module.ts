import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalendarioRoutingModule } from './calendario-routing.module';
import { CalendarioComponent } from './calendario.component';

import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import {MatTabsModule} from '@angular/material/tabs';
import { DemoUtilsModule } from '../utils/module';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CalendarCaidaLibreComponent } from '../calendar-caida-libre/calendar-caida-libre.component';
import { CalendarLeyHookeComponent } from '../calendar-ley-hooke/calendar-ley-hooke.component';
import { CalendarMovParabolicoComponent } from '../calendar-mov-parabolico/calendar-mov-parabolico.component';



@NgModule({
  declarations: [
    CalendarioComponent,
    CalendarCaidaLibreComponent,
    CalendarLeyHookeComponent,
    CalendarMovParabolicoComponent
  ],
  imports: [
    CommonModule,
    CalendarioRoutingModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    DemoUtilsModule,
    MatTabsModule,
    MatInputModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class CalendarioModule { }
