import { Component, OnInit } from '@angular/core';
import { SocialAuthService, SocialUser } from "angularx-social-login";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: SocialAuthService) { }

  user: SocialUser | undefined;
  loggedIn: boolean | undefined;

  ngOnInit(): void {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
    });
  }

  signOut(): void {
    this.authService.signOut();
  }

  validarEmail(): boolean{
    if (this.user?.email.includes("unicauca.edu.co")) {
      return false
    } else {
      return true;
    }
  }

}
