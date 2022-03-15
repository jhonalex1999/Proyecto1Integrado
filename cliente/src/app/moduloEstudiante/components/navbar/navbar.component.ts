import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/service/service.service';
import { VincularmateriaComponent } from '../../vincularmateria/vincularmateria.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public user$ = this.cookieService.get('Token_email');
  public userName$ = this.cookieService.get('Token_name');
  public userPhoto$ = this.cookieService.get('Token_photo');

  codigo: number;

  constructor(public authService: AuthService, private router: Router, private cookieService: CookieService, private httpClient: HttpClient, public dialog: MatDialog) { }

  ngOnInit(): void {
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
