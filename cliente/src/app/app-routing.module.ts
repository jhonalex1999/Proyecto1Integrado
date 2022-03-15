import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CursosComponent } from './moduloProfesor/cursos/cursos.component';

const routes: Routes = [{ 
  path:'',
  redirectTo: '/inicio',
  pathMatch: 'full'
},
{ path: ':id_user/cursos', component: CursosComponent },//,canActivate: [AuthGuard]},
{ path: 'login', loadChildren: () => import('./auth/login/login.module').then(m => m.LoginModule) },
<<<<<<< Updated upstream
  { path: 'inicio', loadChildren: () => import('./moduloEstudiante/inicio/inicio.module').then(m => m.InicioModule) },
  { path: 'calendario', loadChildren: () => import('./moduloEstudiante/calendarios/calendario/calendario.module').then(m => m.CalendarioModule) },
  { path: 'materias', loadChildren: () => import('./moduloEstudiante/materias/materias.module').then(m => m.MateriasModule) },
  { path: 'caidaLibre', loadChildren: () => import('./moduloEstudiante/labs/caida-libre/caida-libre.module').then(m => m.CaidaLibreModule) },
  { path: 'leyHooke', loadChildren: () => import('./moduloEstudiante/labs/ley-hooke/ley-hooke.module').then(m => m.LeyHookeModule) },
  { path: 'movParabolico', loadChildren: () => import('./moduloEstudiante/labs/mov-parabolico/mov-parabolico.module').then(m => m.MovParabolicoModule) },
=======
{ path: 'inicio', loadChildren: () => import('./moduloEstudiante/inicio/inicio.module').then(m => m.InicioModule) },
{ path: 'calendario', loadChildren: () => import('./moduloEstudiante/calendarios/calendario/calendario.module').then(m => m.CalendarioModule) },
{ path: 'materias', loadChildren: () => import('./moduloEstudiante/materias/materias.module').then(m => m.MateriasModule) },
{ path: 'caidaLibre', loadChildren: () => import('./moduloEstudiante/labs/caida-libre/caida-libre.module').then(m => m.CaidaLibreModule) },
{ path: 'leyHooke', loadChildren: () => import('./moduloEstudiante/labs/ley-hooke/ley-hooke.module').then(m => m.LeyHookeModule) },
{ path: 'movParabolico', loadChildren: () => import('./moduloEstudiante/labs/mov-parabolico/mov-parabolico.module').then(m => m.MovParabolicoModule) },
  { path: 'navbar', loadChildren: () => import('./moduloEstudiante/components/navbar/navbar.module').then(m => m.NavbarModule) },
>>>>>>> Stashed changes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
