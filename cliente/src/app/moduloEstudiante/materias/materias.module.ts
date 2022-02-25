import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MateriasRoutingModule } from './materias-routing.module';
import { MateriasComponent } from './materias.component';
import { MatCardModule } from '@angular/material/card';


@NgModule({
  declarations: [
    MateriasComponent
  ],
  imports: [
    CommonModule,
    MateriasRoutingModule,
    MatCardModule
  ]
})
export class MateriasModule { }
