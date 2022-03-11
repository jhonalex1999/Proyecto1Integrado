import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { FranjaHoraria } from '../materia/franja-horaria';
import { FranjaHorariaService } from '../materia/franja-horaria.service';
import { Practica } from '../materia/practica';
import { PracticaService } from '../materia/practica.service';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css']
})
export class AgendaComponent implements OnInit {

  constructor(private franjaService: FranjaHorariaService, private cookieService: CookieService, private practicaService: PracticaService) { }

  public user$ = this.cookieService.get('Token_email');
  public events: any[];
  public options: any;
  public franjaHoraria: FranjaHoraria[];
  public practica: Practica;

  ngOnInit(): void {
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
    }
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

  cargar() {
    for (let index = 0; index < this.franjaHoraria.length; index++) {
      this.practicaService.getById(this.franjaHoraria[index].id_practica).subscribe(
        e => this.practica = e
      );
      this.eventosCargar(this.practica.titulo, index);
    }
  }

  eventosCargar(id: string, index: number) {
    this.events = [{}]
    this.events.push({
      title: this.practica.titulo,
      start: this.franjaHoraria[index].fecha + 'T' + this.franjaHoraria[index].hora_inicio,
      end: this.franjaHoraria[index].fecha + 'T' + this.franjaHoraria[index].hora_fin
    });
  }

}
