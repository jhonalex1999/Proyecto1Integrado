import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  loggedIn: boolean | undefined;
  activar: boolean | undefined;
  urlInicio: any = "";
  urlAgenda: any = "";

  ngOnInit(): void {
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
