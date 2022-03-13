import { Component, OnInit } from '@angular/core';
import { Curso } from './curso';
import { CursoService } from './curso.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/service.service';
import { CookieService } from 'ngx-cookie-service';
import { Practica } from '../materia/practica';
import { PracticaService } from '../materia/practica.service';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent implements OnInit {
  title: string = "Lista de cursos";
  cursos: Curso[];
  practica: Practica[];

  loggedIn: boolean | undefined;
  url: string | undefined;

  constructor(private cursoService: CursoService, private router: Router, private cookieService: CookieService, private practicaService: PracticaService) { }
  public user$ = this.cookieService.get('Token_email');

  ngOnInit(): void {
    this.url = this.router.url;
    this.cursoService.getAllById(this.user$).subscribe(
      e => this.cursos = e
    );

    this.practicaService.getAll().subscribe(
      p => this.practica = p
    );
  }

  delete(curso: Curso): void {
    console.log("Delete curso con ID: " + curso.id_curso);
    this.cursoService.delete(curso.id_curso!).subscribe(
      res => this.cursoService.getAllById(this.router.url.split('/')[1]).subscribe(
        response => this.cursos = response
      )
    );
  }

  deletePractica(practicaBorrar: Practica): void {
    console.log("Delete practica con ID: " + practicaBorrar.id_practica);
    this.practicaService.delete(practicaBorrar.id_practica!).subscribe(
      res => this.practicaService.get(this.router.url.split('/')[3]).subscribe(
        response => this.practica = response
      )
    );
  }

  updatePractica(practica: Practica): void{
    console.log("Update practica con ID: " + practica.id_practica);
    practica.estado = "0";
    this.practicaService.update(practica).subscribe();
  }
}
