import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from './curso';
import { CursoService } from './curso.service';

@Component({
  selector: 'app-form-cursos',
  templateUrl: './form-cursos.component.html',
  styleUrls: ['./form-cursos.component.css']
})
export class FormCursosComponent implements OnInit {

  curso: Curso = new Curso();
  auxIdCurso!: string;

  constructor(private cursoServise: CursoService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.auxIdCurso = this.router.url.split('/')[4];
    console.log(this.auxIdCurso);
    this.activatedRoute.params.subscribe(
      e => {
        let id = e['id'];
        if (id) {
          this.cursoServise.get(id).subscribe(
            es => this.curso = es
          );
        }
      }
    );
  }

  create(): void {
    this.curso.fecha_creacion = "01/01/2022";
    this.curso.fecha_eliminacion = "31/12/2022";
    this.curso.id_docente = this.router.url.split("/")[1];
    console.log(this.curso);
    this.cursoServise.create(this.curso).subscribe(
      res => this.router.navigate([this.router.url.split("/")[1] + '/cursos'])
    );

  }

  update(): void {
    this.curso.fecha_creacion = "01/01/2022";
    this.curso.fecha_eliminacion = "31/12/2022";
    this.cursoServise.update(this.auxIdCurso,this.curso).subscribe(
      res => this.router.navigate([this.router.url.split("/")[1] + '/cursos'])
    );
  }

  rutaLlegada(): boolean {
    if(this.router.url.split('/').length == 4){
      return true;
    }else if(this.router.url.split('/').length == 4){
      return false;
    }
    return false;
  }

}
