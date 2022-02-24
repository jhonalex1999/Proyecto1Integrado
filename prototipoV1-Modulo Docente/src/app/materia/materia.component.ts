import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SocialAuthService, SocialUser } from 'angularx-social-login';
import { Curso } from '../cursos/curso';
import { CursoService } from '../cursos/curso.service';
import { Practica } from './practica';
import { PracticaService } from './practica.service';

@Component({
  selector: 'app-materia',
  templateUrl: './materia.component.html',
  styleUrls: ['./materia.component.css']
})

export class MateriaComponent implements OnInit {

  practica: Practica[] | undefined;
  curso: Curso | undefined;
  url: string | undefined;
  
  user: SocialUser | undefined;
  loggedIn: boolean | undefined;

  constructor(private practicaService:PracticaService,private cursoService:CursoService,
    private authService: SocialAuthService,private router:Router) { }

  ngOnInit() {
    this.practicaService.getAll().subscribe(
      e => this.practica=e
    );

    this.url = this.router.url;

    var id = this.router.url.split('/')[2];

    this.cursoService.get(id).subscribe(
      p => this.curso=p
    );

    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
    });
  }
}