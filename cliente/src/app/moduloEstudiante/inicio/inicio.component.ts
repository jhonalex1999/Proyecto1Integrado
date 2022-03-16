import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Auth } from '@angular/fire/auth';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/service/service.service';
import { VincularmateriaComponent } from '../vincularmateria/vincularmateria.component';
import { environment } from 'src/environments/environment';
import firebase from 'firebase/compat/app';


firebase.initializeApp(environment.firebase);

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
  public userPhoto$ = this.cookieService.get('Token_photo');
  public listado : any = [];

  materias: string;
  codigo: number;

  openDrawerMenu(){
    var x = document.getElementById("opciones")!;
    if (x.className === "opciones"){
      x.className += " responsive";
    } else {
      x.className = "opciones";
    }
  }


  
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
      this.cookieService.delete('Token_photo', '')
      this.router.navigate(['/login']);
    } catch (error) {
      console.log(error);
    }
  }

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
