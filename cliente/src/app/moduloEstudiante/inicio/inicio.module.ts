import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InicioRoutingModule } from './inicio-routing.module';
import { InicioComponent } from './inicio.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NavbarModule } from '../components/navbar/navbar.module';

//import { DemoUtilsModule } from '../calendarios/utils/module';

@NgModule({
  declarations: [
    InicioComponent
  ],
  imports: [
    CommonModule,
    InicioRoutingModule,
    MatDialogModule,
    MatCardModule,
    MatDialogModule,
    MatButtonModule,
    //    DemoUtilsModule
    MatMenuModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatIconModule,
    NavbarModule
  ]
})
export class InicioModule { }
