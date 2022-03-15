import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovParabolicoRoutingModule } from './mov-parabolico-routing.module';
import { MovParabolicoComponent } from './mov-parabolico.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgChartsModule } from 'ng2-charts';
import { CountdownModule } from 'ngx-countdown';
import { NavbarModule } from '../../components/navbar/navbar.module';


@NgModule({
  declarations: [
    MovParabolicoComponent
  ],
  imports: [
    CommonModule,
    MovParabolicoRoutingModule,
    NgxChartsModule,
    CountdownModule,
    NgChartsModule,
    MatDialogModule,
    MatCardModule,
    MatDialogModule,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatIconModule,
    NavbarModule
  ]
})
export class MovParabolicoModule { }
