import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly cookieService: CookieService) {}

  ngOnInit(): void {
  }

  iniciarSesion(){
    this.authService
    .loginWithGoogle()
    .then(res => {
      console.log("Se registro: ", res);
      if (res != null){
        this.cookieService.set('Token_access', res.user.refreshToken ,4, '/');
        this.cookieService.set('Token_email', res.user.email || '' ,4, '/');
        this.cookieService.set('Token_name', res.user.displayName || '' ,4, '/');
        this.authService.cambiarEstadoEntrada();
        this.cookieService.set('Token_photo', res.user.photoURL || '' ,4, '/');
        this.router.navigate(['/inicio']);
      } else {
        this.router.navigate(['/login']);
      }
      
    })
  }

}
