import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SocialUser } from "angularx-social-login";
import { SocialAuthService } from "angularx-social-login";
import { FacebookLoginProvider, GoogleLoginProvider } from "angularx-social-login";
import { Usuario } from './usuario';
import { Usuarioservice } from './usuario.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  usuarios!: Usuario[];
  user: SocialUser | undefined;
  loggedIn: boolean | undefined;

  constructor(private usuarioService: Usuarioservice, private router: Router, private authService: SocialAuthService) { }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
    });
    this.usuarioService.getAll().subscribe(
      e => this.usuarios = e
    );
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    this.authService.signOut();
  }

  validarEmail(): boolean {

    if (this.user?.email.includes("unicauca.edu.co")) {
      return false
    } else {
      return true;
    }
  }

  obtenerID(id: string): void {
    

    for (let index = 0; index < this.usuarios.length; index++) {
      if (this.usuarios[index].nombre_completo == this.user?.name) {
        this.router.navigate([this.usuarios[index].idUsuario + '/cursos'])
      }
    }
  }

}
