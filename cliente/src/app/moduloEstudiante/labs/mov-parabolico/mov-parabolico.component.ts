import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Chart } from 'chart.js';
import { DatabaseReference, get, child } from 'firebase/database';
import { CookieService } from 'ngx-cookie-service';
import { CountdownEvent, CountdownConfig } from 'ngx-countdown';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/service/service.service';
import Swal from 'sweetalert2';

var KEY = 'timeMP';
var DEFAULT = 0; //3600 es 1 hora

@Component({
  selector: 'app-mov-parabolico',
  templateUrl: './mov-parabolico.component.html',
  styleUrls: ['./mov-parabolico.component.scss']
})
export class MovParabolicoComponent implements OnInit {

  db: DatabaseReference;
  tematica: string = '';

  tittle = 'sweetAlert';

  disabled_FinalizarPractica: Boolean = true;
  disabled_FinalizarSimulacion: Boolean = true;
  disabled_Iniciar: boolean = false;
  bandera: Boolean;


  rol: String = "";
  rol$ = this.rol;
  bandera$: Boolean;

  private COD_LAB: number = 3;


  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public userPhoto$ = this.cookieService.get('Token_photo');


  public listadoOpAngulo: any = [];
  public listadoOpVelocidad: any = [];

  public lista1: any = [];
  public lista2: any = [];
  public listadoY: any = [];
  public listadoX: any = [];


  xValues = this.listadoX;
  yValues = this.listadoY;

  duracion$: number = 0;

  constructor(private authSvc: AuthService, private router: Router, private readonly cookieService: CookieService) {

  }

  conexionRealTime() {
    get(child(this.db.ref, 'guias/-LfMZIgigD9gfaBLeupS/tematica')).then((res) => {
      if (res.exists()) {
        console.log(res.val());
        this.tematica = res.val();
      } else {
        console.log("No data available");
      }
    }).catch((error) => {
      console.error(error);
    });
  }


  //public user$: Observable<any> = this.authSvc.afAuth.user;
  ngOnInit(): void {
    this.conexionRealTime();
    this.verificarDuracion();
    this.listarX();
    this.listarY();
    this.authSvc.obtenerOpcionesMP_Angulo(this.COD_LAB).subscribe(respuesta => { this.listadoOpAngulo = respuesta });
    this.authSvc.obtenerOpcionesMP_Velocidad(this.COD_LAB).subscribe(respuesta => { this.listadoOpVelocidad = respuesta });
    this.authSvc.saberRol().subscribe(respuesta => {
      this.rol$ = respuesta
    });
    if (this.cookieService.check('Token_access')) {
      this.router.navigate(['/movparabolico']);
    } else {
      this.router.navigate(['/home'])
    }
    if (this.cookieService.check('Token_access')) {
      this.router.navigate(['/movparabolico']);
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
          yAxes: { min: 0, max: 40 },
        }
      },
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


  public inicio(angulo: any, velocidad: any) {
    angulo = document.getElementById('angulo');
    var val_angulo = angulo.value;
    alert("Angulo: " + val_angulo);
    velocidad = document.getElementById('velocidad');
    var val_velocidad = velocidad.value;
    alert("Velocidad: " + val_velocidad);
    this.authSvc.InicioMovParabolico(val_angulo,val_velocidad).subscribe((result: any) => {
      this.bandera = result
      if (this.bandera == false) {
        console.log('Entro false')
      } else {
        console.log('Entro true');
      }
    });

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

  finalizarSimulaciones() {
    this.disabled_FinalizarSimulacion = false;
    this.disabled_Iniciar= true;
    this.authSvc.finalizarSimulacion(this.COD_LAB).subscribe((result:any) =>{
      if(result == true){
        Swal.fire({
          title: "Practica Movimiento Parabolico",text:"Es Verdadero."
        });
        //alert("Puso true");
      }else{
        Swal.fire({
          title: "Practica Movimiento Parabolico",text:"Es Falso."
        });
        //alert("Puso false");
      }
    });
  }

  finalizarPractica() {
    this.authSvc.saberCodigoGrupo().subscribe(respuesta => {
      this.authSvc.finalizarPractica(respuesta).subscribe((result: any) => { result })
      this.router.navigate(['/materias'])
    });
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

  descargar() {
    this.authSvc.descargar(this.COD_LAB).subscribe((result) => {
      result
      if (result == true) {
        Swal.fire({
          title: "Practica Movimiento Parabolico",text:"Archivo descargado exitosamente, revisa tu carpeta de descargas.",icon:"success"
        });
        //alert("Archivo descargado exitosamente, revisa tu carpeta de descargas")
      }else{
        Swal.fire({
          title:"Practica Movimiento Parabolico",text:"No se ha podido descargar el Archivo.",icon:"error"
        });
        //alert("No se ha podido descargar el Archivo");
      }
    });
    this.disabled_FinalizarPractica = false;
  }

  reportarFalla() {
    console.log("reportarFalla()");
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

  public listarX() {
    console.log("entro a listar alturaaaaaaaaaaaaaa");
    this.authSvc.obtenerDatosMPX(3).pipe(finalize(() => this.prueba())).subscribe((result: any) => {
      this.lista1 = result;
      console.log(result);

      for (var i = 0; i < this.lista1.length; i++) {
        this.listadoY.push(this.lista1[i]);
        console.log(this.listadoY);
      }
    });
  }

  public listarY() {
    console.log("entro a listar alturaaaaaaaaaaaaaa");
    this.authSvc.obtenerDatosMPY(3).pipe(finalize(() => this.prueba())).subscribe((result: any) => {
      this.lista2 = result;
      console.log(result);

      for (var i = 0; i < this.lista2.length; i++) {
        this.listadoX.push(this.lista2[i]);
        console.log(this.listadoX);
      }
    });
  }

  config: CountdownConfig = { leftTime: DEFAULT, notify: 0 };


}
