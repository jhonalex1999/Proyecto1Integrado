import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Auth } from '@angular/fire/auth';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/service/service.service';
import { VincularmateriaComponent } from '../vincularmateria/vincularmateria.component';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss']
})
export class InicioComponent implements OnInit {

  rol$: string;

  constructor(public authService: AuthService, private router: Router, private cookieService: CookieService, private httpClient: HttpClient, private auth: Auth, public dialog: MatDialog) { }
  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public listado : any = [];



  
  ngOnInit(): void {
    this.authService.verCursosMatriculados().subscribe(respuesta => {this.listado = respuesta});
    this.authService.saberRol().subscribe(respuesta => {
      this.rol$ = respuesta
    });
    if (this.cookieService.check('Token_access')) {
      this.router.navigate(['/inicio']);
    } else {
      this.router.navigate(['/login'])
    }
  }

  onLogout() {
    try {
      this.authService.logout();
      this.cookieService.delete('Token_access', '')
      this.cookieService.delete('Token_email', '')
      this.cookieService.delete('Token_name', '')
      this.router.navigate(['/login']);
    } catch (error) {
      console.log(error);
    }
  }

  materias: string;
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
