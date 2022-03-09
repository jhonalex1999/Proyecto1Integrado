import { Component, OnInit } from '@angular/core';
import { Curso } from './curso';
import { CursoService } from './curso.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent implements OnInit {
  title: string = "Lista de cursos";
  cursos!: Curso[];

  loggedIn: boolean | undefined;
  url: string | undefined;

  constructor(private cursoService: CursoService,
    private  authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    

    this.url = this.router.url;
    this.cursoService.getAllById(this.router.url.split('/')[1]).subscribe(
      e => this.cursos = e
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
}