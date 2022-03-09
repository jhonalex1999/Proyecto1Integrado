import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovParabolicoRoutingModule } from './mov-parabolico-routing.module';
import { MovParabolicoComponent } from './mov-parabolico.component';


@NgModule({
  declarations: [
    MovParabolicoComponent
  ],
  imports: [
    CommonModule,
    MovParabolicoRoutingModule
  ]
})
export class MovParabolicoModule { }
