import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MateriasRoutingModule } from './materias-routing.module';
import { MateriasComponent } from './materias.component';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NavbarModule } from '../components/navbar/navbar.module';


@NgModule({
  declarations: [
    MateriasComponent
  ],
  imports: [
    CommonModule,
    MateriasRoutingModule,
    MatCardModule,
    MatDialogModule,
    MatDialogModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatIconModule,
    NavbarModule
  ]
})
export class MateriasModule { }
