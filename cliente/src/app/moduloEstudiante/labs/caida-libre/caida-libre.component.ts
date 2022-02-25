import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-caida-libre',
  templateUrl: './caida-libre.component.html',
  styleUrls: ['./caida-libre.component.scss']
})
export class CaidaLibreComponent implements OnInit {
  rol: String = "";
  rol$ = this.rol;
  //bandera : Boolean = false;
  bandera$: Boolean;


  constructor(private authSvc: AuthService, private router: Router, private readonly cookieService: CookieService) { }

  //public user$: Observable<any> = this.authSvc.afAuth.user;
  ngOnInit(): void {
    this.authSvc.saberRol().subscribe(respuesta => {
      this.rol$ = respuesta
    });
    if (this.cookieService.check('Token_access')) {
      this.router.navigate(['/caidalibre']);
    } else {
      this.router.navigate(['/home'])
    }
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

  /*public eslider (): boolean{
    if(this.rol.indexOf('Lider') ){
      return false;
    }
    return true;
    
  }

  public esespectador(): boolean {
    if(this.rol.indexOf('Observador')){
      return false;
    }
    return true;
  }

  public liderpreparado(): boolean {
    if(this.variableespectadores.indexOf('preparado')){
      return false;
    }
    return true;
  }

  public lidernopreparado(): boolean {
    if(this.liderpreparado() == true){
      return false;
    }
    return true;
  }*/






  descargar() {
    //this.authSvc.descargar();
  }

  async Ontraerol() {
    try {
      await this.authSvc.traerrol();

    } catch (error) {
      console.log(error);
    }
  }

}
