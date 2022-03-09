import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/service.service';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private cookieService: CookieService) { }
  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public photo$ = this.cookieService.get('Token_photo');
  
  loggedIn: boolean | undefined;
  activar: boolean | undefined;
  urlInicio: any = "";
  urlAgenda: any = "";

  ngOnInit(): void {
    this.urlAgenda = this.router.url + '/agenda';
  }

  signOut(): void {
    this.authService.logout();
  }

  
  cargarNavBar(): boolean {
    this.urlInicio = this.router.url.split('/')[1]+"/cursos";
    this.urlAgenda = this.router.url.split('/')[1]+"/agenda";
    if (this.router.url.includes("curso") || this.router.url.includes("agenda")) {
      return true;
    } else {
      return false;
    }
  }
}
