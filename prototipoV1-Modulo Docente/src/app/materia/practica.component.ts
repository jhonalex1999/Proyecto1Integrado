import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin, { Draggable } from '@fullcalendar/interaction';
import { Practica } from './practica';
import { PracticaService } from './practica.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-practica',
  templateUrl: './practica.component.html',
  styleUrls: ['./practica.component.css']
})
export class PracticaComponent implements OnInit {

  //Variables del Calendario-Observable
  public events: any[] | undefined;
  public options: any;
  public practica!: Practica[];
  public activarCalendario: boolean = false;
  url!: string;

  //Variables Form
  practicaNueva: Practica = new Practica();
  dateHora: string = "";
  dateDia: string = "";

  //timepicker
  time = { hour: 13, minute: 30 };
  meridian = true;
  
  toggleMeridian() {
    this.meridian = !this.meridian;
  }

  constructor(private practicaService: PracticaService, private router: Router) { }

  ngOnInit() {
    this.url = this.router.url;

    this.practicaService.getAll().subscribe(
      e => this.practica = e
    );

    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      defaultDate: new Date(),
      locale: 'es',
      editable: true,
      eventLimit: false,
      dropabble: true,
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
    this.practicaService.getAll().subscribe(
      e => this.practica = e
    );

    this.events = [{}]

    for (let index = 0; index < this.practica.length; index++) {
      this.events.push({
        title: this.practica[index].titulo,
        start: this.practica[index].fecha_entrega
      });
    }
  }

  cargarCalendario(): boolean {
    this.cargar();
    if (this.activarCalendario == true) {
      this.activarCalendario = false;
      return true;
    } else {
      this.activarCalendario = true;
      return false;
    }
  }

  create(): void {
    this.practicaNueva.id_curso = this.router.url.split('/')[2];
    this.practicaNueva.estado = "activo";
    this.practicaNueva.fecha_entrega = this.dateDia+'T'+this.dateHora+':00';

    var back = this.url.split('/practica');
    this.practicaService.create(this.practicaNueva).subscribe(
      res=>this.router.navigate([back[0]])
    );
    
  }
}