import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LeyHookeRoutingModule } from './ley-hooke-routing.module';
import { LeyHookeComponent } from './ley-hooke.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { CountdownModule } from 'ngx-countdown';


@NgModule({
  declarations: [
    LeyHookeComponent
  ],
  imports: [
    CommonModule,
    LeyHookeRoutingModule,
    NgxChartsModule,
    CountdownModule
  ]
})
export class LeyHookeModule { }
