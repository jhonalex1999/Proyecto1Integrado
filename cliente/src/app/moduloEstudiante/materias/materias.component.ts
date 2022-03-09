import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/service/service.service';
import { VincularmateriaComponent } from '../vincularmateria/vincularmateria.component';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['./materias.component.scss']
})
export class MateriasComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private cookieService: CookieService, public dialog: MatDialog) { }

  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public userPhoto$ = this.cookieService.get('Token_photo');


  

  ngOnInit(): void {
  }

  bandera: Boolean = false;

  codigo_planta: number;
  verificarAgendamiento(codigo_planta:number) {
    this.codigo_planta = codigo_planta;
    this.authService.saberCodigoGrupo().subscribe(respuesta=> {this.authService.verificarAgendamientoGrupo(respuesta,codigo_planta).pipe(finalize(() => this.prueba())).subscribe((result: any) => { this.bandera=result })
  });
  }

  prueba(){
    if (this.bandera) {
      if(this.codigo_planta==1){
        this.router.navigate(['/caidalibre']);
      }else if(this.codigo_planta==2){
        this.router.navigate(['/leyhooke']);
      }else if(this.codigo_planta==3){
        this.router.navigate(['/movparabolico']);
      }
      
    } else {
      alert('No tienes practicas agendadas en este horario');
  
    }
  }

  codigo: number;

  openDialog(): void {
    const dialogRef = this.dialog.open(VincularmateriaComponent, {
      data: { name: this.codigo }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.codigo = result;
    });
  }
}
