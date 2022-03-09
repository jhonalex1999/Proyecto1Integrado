import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import { FranjaHoraria } from '../materia/franja-horaria';
import { FranjaHorariaService } from '../materia/franja-horaria.service';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css']
})
export class AgendaComponent implements OnInit {

  constructor(private franjaService: FranjaHorariaService, private cookieService: CookieService) { }
  
  public user$ = this.cookieService.get('Token_email');
  public events: any[] | undefined;
  public options: any;
  public franjaHoraria!: FranjaHoraria[];


  ngOnInit(): void {
    /*this.franjaService.getAll().subscribe(
      p => this.franjaHoraria = p
    );*/

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

    this.events = [{}];
  }

  validarHorario(): boolean{
    try {
      if(this.franjaHoraria.length){
        this.cargar();
        return true;
      }else{
        return false;
      }
    } catch (error) {
      return false;
    }
    
  }

  cargar(): void {
    this.events = [{}]
    for (let index = 0; index < this.franjaHoraria.length; index++) {
      this.events.push({
        title: this.franjaHoraria[index].titulo, //Titulo practica
        start: this.franjaHoraria[index].fecha_inicio, //'2022-02-21T10:00:00' 
        end: this.franjaHoraria[index].fecha_fin
      });
    }
  }

}
