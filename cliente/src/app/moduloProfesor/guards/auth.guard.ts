import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { SocialAuthService, SocialUser } from 'angularx-social-login';
import { Observable } from 'rxjs';
import { Usuarioservice } from '../login/usuario.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  loggedIn: boolean | undefined;

  constructor(private usuarioService: Usuarioservice, private router: Router, private authService: SocialAuthService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.authService.authState.subscribe((user) => {
      this.loggedIn = (user != null);
    });
    if (this.loggedIn) {
      return true;
    } else {
      this.router.navigate([''])
      return false;
    }
  }

}
