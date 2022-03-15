import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { CountdownEvent, CountdownConfig } from 'ngx-countdown';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/service/service.service';
import { Chart, ChartConfiguration, ChartData, ChartType } from 'chart.js';
import Swal from 'sweetalert2';


var KEY = 'timeCL';
var DEFAULT = 0; //3600 es 1 hora


@Component({
  selector: 'app-caida-libre',
  templateUrl: './caida-libre.component.html',
  styleUrls: ['./caida-libre.component.scss']
})
export class CaidaLibreComponent implements OnInit {
  tittle = 'sweetAlert';

  rol: String = "";
  rol$ = this.rol;
  bandera: Boolean;
  bandera$: Boolean;

  private COD_LAB: number = 2;
  public lista1: any = [];
  public lista2: any = [];
  public listadoAltura: any = [];
  public listadoTiempo: any = [];


  xValues = this.listadoTiempo;
  yValues = this.listadoAltura;

  duracion$: number = 0;

  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public userPhoto$ = this.cookieService.get('Token_photo');


  public listadoOpciones: any = [];

  disabled_FinalizarPractica: Boolean = true;
  disabled_FinalizarSimulacion: Boolean = true;
  disabled_Iniciar: boolean = false;

  storageCaidaLibre: Storage;

  constructor(private authSvc: AuthService, private router: Router, private readonly cookieService: CookieService) { }

  ngOnInit(): void {
    this.verificarDuracion();
    this.listarAltura();
    this.listarTiempo();
    this.authSvc.obtenerOpcionesCL_Repeticiones(this.COD_LAB).subscribe(respuesta => { this.listadoOpciones = respuesta });
    this.authSvc.saberRol().subscribe(respuesta => {
      this.rol$ = respuesta
    });
    if (this.cookieService.check('Token_access')) {
      this.router.navigate(['/caidalibre']);
    } else {
      this.router.navigate(['/login']);
    }


    //Cuenta regresiva
    let value = +localStorage.getItem(KEY)!! ?? DEFAULT;
    if (value <= 0) value = DEFAULT;
    this.config = { ...this.config, leftTime: value };

    new Chart("myChart", {
      type: "line",
      data: {
        labels: this.xValues,
        datasets: [{
          backgroundColor: "rgba(0,0,0,1.0)",
          borderColor: "rgba(0,0,0,0.1)",
          data: this.yValues,
          label: 'Mostrar Grafica (Confirmar)',
        }],
      },
      options: {
        scales: {
          yAxes: { min: 0, max: 50 },
        }
      },
    });

  }

  public inicio(peso:any) {
    peso = document.getElementById('peso');
    var val_peso = peso.value;
    alert("Peso: " + val_peso);
    this.authSvc.InicioCaidaLibre(val_peso).subscribe((result: any) => {
      this.bandera = result
      if (this.bandera == false) {
        console.log('Entro false')
      } else {
        console.log('Entro true');
      }
    });
  }



  handleEvent(ev: CountdownEvent) {
    if (ev.action === 'notify') {
      // Save current value
      localStorage.setItem(KEY, `${ev.left / 1000}`);
    }

  }
  timesUp(event: CountdownEvent) {
    if (event.action == "done") {
      console.log("Finished");
      localStorage.clear();
      localStorage.removeItem(KEY);
      this.router.navigate(['/materias']);
    }
  }


  finalizarSimulaciones() {
    this.disabled_FinalizarSimulacion = false;
    this.disabled_Iniciar= true;
    this.authSvc.finalizarSimulacion(this.COD_LAB).subscribe((result:any) =>{
      if(result == true){
        Swal.fire({
          title: "Practica Caida Libre",text:"Es Verdadero."
        });
        //alert("Puso true");
      }else{
        Swal.fire({
          title: "Practica Caida Libre",text:"Es Falso."
        });
        //alert("Puso false");
      }
    });

  }

  descargar() {
    this.authSvc.descargar(this.COD_LAB).subscribe((result) => {
      result
      if (result == true) {
        Swal.fire({
          title: "Practica Caida Libre",text:"Archivo descargado exitosamente, revisa tu carpeta de descargas.",icon:"success"
        });
        //alert("")
      }else{
        Swal.fire({
          title:"Practica Caida Libre",text:"No se ha podido descargar el Archivo.",icon:"error"
        });
        //alert("");
      }
    });
    this.disabled_FinalizarPractica = false;
  }


  alerta() {
    Swal.fire({
      title: 'Estás saliendo de la practica!',
      text: "Ten cuidado estás por salir de la practica",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Si, deseo salir'
    }).then((result) => {
      if (result.isConfirmed) {
        this.router.navigate(['/materias'])
      }
    })
  }
  closeSwal() {
    throw new Error('Method not implemented.');
  }

  finalizarPractica() {
    this.authSvc.saberCodigoGrupo().subscribe(respuesta => {
      this.authSvc.finalizarPractica(respuesta).subscribe((result: any) => { result })
      this.router.navigate(['/materias'])
    });
  }

  reportarFalla() {
    console.log("reportarFalla()");
  }


  prueba() {
    if (this.bandera$ == false) {
      return false;
    } else {
      return true;
    }
  }

  verificar() {
    this.authSvc.saberCodigoGrupo().subscribe(respuesta => {
      this.authSvc.verificarGrupoCompleto(respuesta).pipe(finalize(() => this.prueba())).subscribe((result: any) => { this.bandera$ = result })
    });
    //window.location.reload();
  }

  verificarDuracion() {
    this.authSvc.saberCodigoGrupo().subscribe(respuesta => {
      this.authSvc.duracionPractica(respuesta, this.COD_LAB).pipe(finalize(() => this.prueba())).subscribe((result: any) => {
        console.log(result);
        this.duracion$ = result;
        DEFAULT = this.duracion$;
        console.log(DEFAULT);
      })
    });
  }

  public listarAltura() {
    console.log("entro a listar alturaaaaaaaaaaaaaa");
    this.authSvc.obtenerDatosCLAltura(2).pipe(finalize(() => this.prueba())).subscribe((result: any) => {
      this.lista1 = result;
      console.log(result);

      for (var i = 0; i < this.lista1.length; i++) {
        this.listadoAltura.push(this.lista1[i]);
        console.log(this.listadoAltura);
      }
    });
  }

  public listarTiempo() {
    console.log("entro a listar alturaaaaaaaaaaaaaa");
    this.authSvc.obtenerDatosCLTiempo(2).pipe(finalize(() => this.prueba())).subscribe((result: any) => {
      this.lista2 = result;
      console.log(result);

      for (var i = 0; i < this.lista2.length; i++) {
        this.listadoTiempo.push(this.lista2[i]);
        console.log(this.listadoTiempo);
      }
    });
  }

  config: CountdownConfig = { leftTime: DEFAULT, notify: 0 };}
