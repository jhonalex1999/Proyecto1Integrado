import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CaidaLibreRoutingModule } from './caida-libre-routing.module';
import { CaidaLibreComponent } from './caida-libre.component';


@NgModule({
  declarations: [
    CaidaLibreComponent
  ],
  imports: [
    CommonModule,
    CaidaLibreRoutingModule
  ]
})
export class CaidaLibreModule { }
