import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { environment } from '../environments/environment';
import { provideAnalytics, getAnalytics, ScreenTrackingService, UserTrackingService } from '@angular/fire/analytics';
import { provideAuth, getAuth } from '@angular/fire/auth';
import { provideDatabase, getDatabase } from '@angular/fire/database';
import { provideFirestore, getFirestore } from '@angular/fire/firestore';
import { provideFunctions, getFunctions } from '@angular/fire/functions';
import { provideMessaging, getMessaging } from '@angular/fire/messaging';
import { providePerformance, getPerformance } from '@angular/fire/performance';
import { provideRemoteConfig, getRemoteConfig } from '@angular/fire/remote-config';
import { provideStorage, getStorage } from '@angular/fire/storage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CookieService } from 'ngx-cookie-service';
import { FormsModule } from '@angular/forms';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { MatFormFieldModule } from '@angular/material/form-field';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { VincularmateriaComponent } from './moduloEstudiante/vincularmateria/vincularmateria.component';
import { AgendaComponent } from './moduloProfesor/agenda/agenda.component';
import { MateriaComponent } from './moduloProfesor/materia/materia.component';

import { MatMenuModule } from '@angular/material/menu';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { CdkAccordionModule } from '@angular/cdk/accordion';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatBadgeModule } from '@angular/material/badge';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableModule } from '@angular/material/table';
import { CursosComponent } from './moduloProfesor/cursos/cursos.component';
import { FormCursosComponent } from './moduloProfesor/cursos/form-cursos.component';
import { NavbarComponent } from './moduloProfesor/navbar/navbar.component';

import { FullCalendarModule } from 'primeng/fullcalendar';
import { ReactiveFormsModule } from '@angular/forms';
import { PracticaComponent } from './moduloProfesor/materia/practica.component';
import { UsuarioComponent } from './usuario/usuario.component';

@NgModule({
  declarations: [
    AppComponent,
    VincularmateriaComponent,
    CursosComponent,
    FormCursosComponent,
    NavbarComponent,
    AgendaComponent,
    PracticaComponent,
    MateriaComponent,
    FormCursosComponent,
    UsuarioComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAnalytics(() => getAnalytics()),
    provideAuth(() => getAuth()),
    provideDatabase(() => getDatabase()),
    provideFirestore(() => getFirestore()),
    provideFunctions(() => getFunctions()),
    provideMessaging(() => getMessaging()),
    providePerformance(() => getPerformance()),
    provideRemoteConfig(() => getRemoteConfig()),
    provideStorage(() => getStorage()),
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDialogModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    NgbModule,
    [HttpClientModule,],
    MatMenuModule,
    MatExpansionModule,
    MatIconModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
    MatStepperModule,
    CdkAccordionModule,
    MatSelectModule,
    MatBadgeModule,
    MatDatepickerModule,
    MatChipsModule,
    FullCalendarModule,
    MatTableModule,

  ],
  providers: [
    ScreenTrackingService, UserTrackingService, [CookieService]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
