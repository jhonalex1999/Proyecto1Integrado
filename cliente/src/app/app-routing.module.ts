import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgendaComponent } from './moduloProfesor/agenda/agenda.component';
import { CursosComponent } from './moduloProfesor/cursos/cursos.component';
import { FormCursosComponent } from './moduloProfesor/cursos/form-cursos.component';
import { EstudianteComponent } from './moduloProfesor/materia/estudiante/estudiante.component';
import { MateriaComponent } from './moduloProfesor/materia/materia.component';
import { PracticaComponent } from './moduloProfesor/materia/practica.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { GuardsGuard } from './guards.guard';

const routes: Routes = [{
  path: '',
  redirectTo: '/login',
  pathMatch: 'full'
},
{ path: ':correo/cursos', component: CursosComponent },
{ path: ':correo/usuario', component: UsuarioComponent },
{ path: ':correo/cursos/:id/form', component: FormCursosComponent },
{ path: ':correo/cursos/form', component: FormCursosComponent },
{ path: ':correo/agenda', component: AgendaComponent },
{ path: ':correo/cursos/:id/materia', component: MateriaComponent },
{ path: ':correo/cursos/:id/materia/:nombre/estudiante', component: EstudianteComponent },
{ path: ':correo/cursos/:id/materia/practica', component: PracticaComponent },
{ path: ':correo/cursos/:id/materia/:id/practica', component: PracticaComponent },


{ path: 'login', loadChildren: () => import('./auth/login/login.module').then(m => m.LoginModule) },
{ path: 'inicio', loadChildren: () => import('./moduloEstudiante/inicio/inicio.module').then(m => m.InicioModule),canActivate:[GuardsGuard] },
{ path: 'caidalibre', loadChildren: () => import('./moduloEstudiante/labs/caida-libre/caida-libre.module').then(m => m.CaidaLibreModule),canActivate:[GuardsGuard] },
{ path: 'leyhooke', loadChildren: () => import('./moduloEstudiante/labs/ley-hooke/ley-hooke.module').then(m => m.LeyHookeModule),canActivate:[GuardsGuard] },
{ path: 'movparabolico', loadChildren: () => import('./moduloEstudiante/labs/mov-parabolico/mov-parabolico.module').then(m => m.MovParabolicoModule),canActivate:[GuardsGuard] },
{ path: 'calendario', loadChildren: () => import('./moduloEstudiante/calendarios/calendario/calendario.module').then(m => m.CalendarioModule),canActivate:[GuardsGuard] },
{ path: 'materias', loadChildren: () => import('./moduloEstudiante/materias/materias.module').then(m => m.MateriasModule),canActivate:[GuardsGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
