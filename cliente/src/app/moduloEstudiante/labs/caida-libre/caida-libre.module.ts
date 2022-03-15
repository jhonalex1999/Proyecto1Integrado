import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CaidaLibreRoutingModule } from './caida-libre-routing.module';
import { CaidaLibreComponent } from './caida-libre.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgChartsModule } from 'ng2-charts';
import { CountdownModule } from 'ngx-countdown';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatMenuModule } from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { NavbarModule } from '../../components/navbar/navbar.module';


@NgModule({
  declarations: [
    CaidaLibreComponent
  ],
  imports: [
    CommonModule,
    CaidaLibreRoutingModule,
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
    NavbarModule,
  ]
})
export class CaidaLibreModule { }
