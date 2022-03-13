import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/service.service';
import { CookieService } from 'ngx-cookie-service';
import { ProblemaService } from 'src/app/problema/problema.service';
import { Problema } from 'src/app/problema/problema';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private cookieService: CookieService,
    private problemaService: ProblemaService) { }
  
  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public photo$ = this.cookieService.get('Token_photo');
  public rol$ = this.cookieService.get('Token_rol');
  problemas: Problema[];

  loggedIn: boolean | undefined;
  activar: boolean | undefined;
  urlInicio: any = "";
  urlAgenda: any = "";
  urlUsuario: any = "";

  ngOnInit(): void {
    this.urlAgenda = this.router.url + '/agenda';
    this.urlUsuario = this.router.url + '/' + this.user$ +'/usuario';

    this.problemaService.get().subscribe(
      res => this.problemas = res
    );
    
  }

  signOut(): void {
    this.authService.logout();
  }

  
  cargarNavBar(): boolean {
    this.urlInicio = this.router.url.split('/')[1]+"/cursos";
    this.urlAgenda = this.router.url.split('/')[1]+"/agenda";
    if (this.router.url.includes("curso") || this.router.url.includes("agenda") || this.router.url.includes("usuario")) {
      return true;
    } else {
      return false;
    }
  }
}
