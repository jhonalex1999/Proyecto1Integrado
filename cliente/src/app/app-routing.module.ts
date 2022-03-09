import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgendaComponent } from './moduloProfesor/agenda/agenda.component';
import { CursosComponent } from './moduloProfesor/cursos/cursos.component';

const routes: Routes = [{
  path: '',
  redirectTo: '/inicio',
  pathMatch: 'full'
},
{ path: ':correo/cursos', component: CursosComponent },
{ path: ':correo/agenda', component: AgendaComponent },


{ path: 'login', loadChildren: () => import('./auth/login/login.module').then(m => m.LoginModule) },
{ path: 'inicio', loadChildren: () => import('./moduloEstudiante/inicio/inicio.module').then(m => m.InicioModule) },
{ path: 'calendario', loadChildren: () => import('./moduloEstudiante/calendarios/calendario/calendario.module').then(m => m.CalendarioModule) },
{ path: 'materias', loadChildren: () => import('./moduloEstudiante/materias/materias.module').then(m => m.MateriasModule) },
{ path: 'caidaLibre', loadChildren: () => import('./moduloEstudiante/labs/caida-libre/caida-libre.module').then(m => m.CaidaLibreModule) },
{ path: 'leyHooke', loadChildren: () => import('./moduloEstudiante/labs/ley-hooke/ley-hooke.module').then(m => m.LeyHookeModule) },
{ path: 'movParabolico', loadChildren: () => import('./moduloEstudiante/labs/mov-parabolico/mov-parabolico.module').then(m => m.MovParabolicoModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
