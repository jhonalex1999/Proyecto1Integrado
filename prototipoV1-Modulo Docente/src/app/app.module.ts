import { SocialLoginModule, SocialAuthServiceConfig } from 'angularx-social-login';
import {
  GoogleLoginProvider,
  FacebookLoginProvider
} from 'angularx-social-login';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Routes, RouterModule } from '@angular/router';

import { FullCalendarModule } from 'primeng/fullcalendar'; 

import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { CursosComponent } from './cursos/cursos.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FormCursosComponent } from './cursos/form-cursos.component';
import { MateriaComponent } from './materia/materia.component';
import { PracticaComponent } from './materia/practica.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: ':id_user/cursos', component: CursosComponent },
  { path: ':id_user/cursos/form', component: FormCursosComponent },
  { path: ':id_user/cursos/form/:id', component: FormCursosComponent },
  { path: ':id_user/cursos/:id/materia', component: MateriaComponent },
  { path: ':id_user/cursos/:id/materia/practica', component: PracticaComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CursosComponent,
    NavbarComponent,
    FormCursosComponent,
    MateriaComponent,
    PracticaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SocialLoginModule,
    RouterModule.forRoot(routes),
    FormsModule,
    FullCalendarModule
  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '70913171908-t6s5e4ogp6aejs7492dg4djrmf371eu3.apps.googleusercontent.com'
            )
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('clientId')
          }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
