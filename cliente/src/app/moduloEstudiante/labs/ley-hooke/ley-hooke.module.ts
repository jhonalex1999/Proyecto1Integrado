import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LeyHookeRoutingModule } from './ley-hooke-routing.module';
import { LeyHookeComponent } from './ley-hooke.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { CountdownModule } from 'ngx-countdown';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NgChartsModule } from 'ng2-charts';
import { NavbarModule } from '../../components/navbar/navbar.module';


@NgModule({
  declarations: [
    LeyHookeComponent
  ],
  imports: [
    CommonModule,
    LeyHookeRoutingModule,
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
export class LeyHookeModule { }
