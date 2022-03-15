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

    this.practicaService.get(this.router.url.split('/')[3]).subscribe(
      res => this.practica = res
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
    this.events = [{}]
    for (let index = 0; index < this.franjaHoraria.length; index++) {
      this.practicaService.getById(this.franjaHoraria[index].id_practica).subscribe(
        e => this.practicaUnica = e
      );
      this.eventosCargar(this.practicaUnica.titulo, index);
    }
  }

  eventosCargar(id: string, index: number) {

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

  async create(): Promise<void> {
    //Practica Nueva
    this.practicaNueva.id_curso = this.router.url.split('/')[3];
    this.practicaNueva.estado = "1";
    this.practicaNueva.fecha_entrega = this.dateDia + 'T' + this.dateHora + ':00';
    //Subir Archivos
    if (this.event!) {
      this.subirArchivos();
    }

    console.log(this.practicaNueva.archivos);
    var back = this.url.split('/practica');

    setTimeout(() => {
      this.practicaService.create(this.practicaNueva).subscribe(
        res => this.router.navigate([back[0]])
      );
    }, 3500);
    

    setTimeout(() => {
      this.recargar();
    }, 1500);
  }

  recargar() {
    this.practicaService.get(this.router.url.split('/')[3]).subscribe(
      res => this.practica = res
    );

    setTimeout(() => {
      this.crearFranja();
    }, 1500);
  }

  crearFranja(): void {

    for (let i = 0; i < this.practica.length; i++) {
      if (this.practica[i].titulo == this.practicaNueva.titulo) {
        this.franjaNueva.id_practica = this.practica[i].id_practica;
      }
    }

    //Franja Nueva
    this.franjaNueva.fecha = this.dateInicioDia;
    this.franjaNueva.hora_inicio = this.dateInicioHora + ':00';
    this.franjaNueva.hora_fin = this.dateFinHora + ':00';

    this.franjaService.create(this.franjaNueva).subscribe();
  }

  event: any;
  cargarEvento(eAux: any) {
    this.event = eAux;
  }

  archivosFuera: any[] = [];
  subirArchivos() {
    let archivos = this.event.target.files;
    let aux;
    for (let index = 0; index < archivos.length; index++) {
      let reader = new FileReader();
      reader.readAsDataURL(archivos[index]);
      reader.onloadend = async () => {
        this.archivosFuera.push(reader.result);
        aux = await this.subirArchivosFire(await this.router.url.split('/')[3] + '/' + archivos[index].name, await reader.result);
        await this.practicaNueva.archivos.push(aux);
      }
    }
  }
  
  async subirArchivosFire(nombre: string, imgBase64: any): Promise<any> {
    let respuesta = await this.storageRef.child(nombre).putString(imgBase64, 'data_url');
    return await respuesta.ref.getDownloadURL();
  }
}