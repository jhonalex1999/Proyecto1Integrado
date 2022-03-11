import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Curso } from '../cursos/curso';
import { CursoService } from '../cursos/curso.service';
import { FranjaHoraria } from './franja-horaria';
import { FranjaHorariaService } from './franja-horaria.service';
import { Practica } from './practica';
import { PracticaService } from './practica.service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-materia',
  templateUrl: './materia.component.html',
  styleUrls: ['./materia.component.css']
})

export class MateriaComponent implements OnInit {

  //Calendario
  public events: any[] | undefined;
  public options: any;
  public activarCalendario: boolean = false;
  public franjaHoraria!: FranjaHoraria[];

  practica: Practica[] | undefined;
  curso: Curso | undefined;
  url: string | undefined;

  loggedIn: boolean | undefined;

  constructor(private practicaService: PracticaService, private cursoService: CursoService,private router: Router, private cookieService: CookieService) { }

  public user$ = this.cookieService.get('Token_email');
  ngOnInit() {

    this.practicaService.getAll().subscribe(
      e => this.practica = e
    );

    this.url = this.router.url;
    var id = this.router.url.split('/')[3];
    this.cursoService.get(id).subscribe(
      c => this.curso = c
    );

  }

  delete(practicaBorrar:Practica): void{
    console.log("Delete practica con ID: "+practicaBorrar.idpractica);
    this.practicaService.delete(practicaBorrar.idpractica!).subscribe(
      res=>this.practicaService.getAll().subscribe(
        response=>this.practica=response
      )
    );
  }
}