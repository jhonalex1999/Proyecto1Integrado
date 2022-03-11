import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { Practica } from './practica';
import { PracticaService } from './practica.service';
import { FranjaHorariaService } from './franja-horaria.service';
import { Router } from '@angular/router';
import { FranjaHoraria } from './franja-horaria';

import firebase from 'firebase/compat/app';
import 'firebase/compat/storage';
import { environment } from 'src/environments/environment';
import { CookieService } from 'ngx-cookie-service';

firebase.initializeApp(environment.firebase);
@Component({
  selector: 'app-practica',
  templateUrl: './practica.component.html',
  styleUrls: ['./practica.component.css']
})


export class PracticaComponent implements OnInit {

  //Variables del Calendario-Observable
  public events: any[] | undefined;
  public options: any;
  public practica: Practica[];
  public franjaHoraria: FranjaHoraria[];
  url!: string;
  public practicaUnica: Practica;

  //Variables Form
  practicaNueva: Practica = new Practica();
  dateHora: string = "";
  dateDia: string = "";

  //FranjaHoraria
  franjaNueva: FranjaHoraria = new FranjaHoraria();
  dateInicioHora: string = "";
  dateInicioDia: string = "";
  dateFinHora: string = "";
  dateFinDia: string = "";

  //timepicker
  time = { hour: 13, minute: 30 };
  meridian = true;

  //Subir Documentos
  storageRef = firebase.app().storage().ref();

  toggleMeridian() {
    this.meridian = !this.meridian;
  }

  constructor(private practicaService: PracticaService, private franjaService: FranjaHorariaService, private router: Router, private cookieService: CookieService) { }

  public user$ = this.cookieService.get('Token_email');
  ngOnInit() {
    this.url = this.router.url;

    this.practicaService.get(this.user$).subscribe(
      e => this.practica = e
    );

    this.franjaService.getAll().subscribe(
      p => this.franjaHoraria = p
    );

    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin],
      defaultDate: new Date(),
      locale: 'es',
      editable: false,
      eventLimit: false,
      dropabble: false,
      header: {
        left: 'prev,next',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },

      navLinks: true,
      navLinkWeekClick: function (weekStart: { toISOString: () => any; }, jsEvent: { pageX: any; pageY: any; }) {
        console.log('week start', weekStart.toISOString());
        console.log('coords', jsEvent.pageX, jsEvent.pageY);
      },
    };

  }

  cargar(): void {
    for (let index = 0; index < this.franjaHoraria.length; index++) {
      this.practicaService.getById(this.franjaHoraria[index].id_practica).subscribe(
        e => this.practicaUnica = e
      );
      this.eventosCargar(this.practicaUnica.titulo, index);
    }
  }

  eventosCargar(id: string, index: number){
    this.events = [{}]
    this.events.push({
      title: this.practicaUnica.titulo,
      start: this.franjaHoraria[index].fecha + 'T' + this.franjaHoraria[index].hora_inicio,
      end: this.franjaHoraria[index].fecha + 'T' + this.franjaHoraria[index].hora_fin
    });
  }

  public band: boolean = false;
  async validarHorario(): Promise<boolean> {
    try {
      if (this.franjaHoraria.length && this.band == false) {
        await this.cargar();
        this.band = true;
        return true;
      } else {
        return false;
      }
    } catch (error) {
      return true;
    }

  }

  create(): void {
    //Practica Nueva
    this.practicaNueva.id_curso = this.router.url.split('/')[3];
    this.practicaNueva.estado = "1";
    this.practicaNueva.fecha_entrega = this.dateDia + 'T' + this.dateHora + ':00';

    //Franja Nueva
    this.franjaNueva.id_practica = this.practicaNueva.idpractica;
    this.franjaNueva.fecha = this.practicaNueva.id_curso;
    this.franjaNueva.hora_inicio = this.dateInicioDia + 'T' + this.dateInicioHora + ':00';
    this.franjaNueva.hora_fin = this.dateFinDia + 'T' + this.dateFinHora + ':00';

    console.log(this.router.url.split('/')[3]);
    var back = this.url.split('/practica');

    //Subir Archivos
    if (this.event!) {
      this.subirArchivos();
    }

    //this.franjaService.create(this.franjaNueva).subscribe();
    this.practicaService.create(this.practicaNueva).subscribe(
      res => this.router.navigate([back[0]])
    );

  }

  event: any;
  cargarEvento(eAux: any) {
    this.event = eAux;
  }

  archivosFuera: any[] = [];
  subirArchivos() {
    let archivos = this.event.target.files;

    for (let index = 0; index < archivos.length; index++) {
      let reader = new FileReader();
      reader.readAsDataURL(archivos[index]);
      reader.onloadend = () => {
        this.archivosFuera.push(reader.result);
        this.subirArchivosFire(this.router.url.split('/')[3] + '/' + archivos[index].name, reader.result);
      }
    }
    //console.log(file.name);

  }

  async subirArchivosFire(nombre: string, imgBase64: any) {
    let respuesta = await this.storageRef.child(nombre).putString(imgBase64, 'data_url');
    console.log(respuesta.ref.getDownloadURL());
    return await respuesta.ref.getDownloadURL();
  }
}