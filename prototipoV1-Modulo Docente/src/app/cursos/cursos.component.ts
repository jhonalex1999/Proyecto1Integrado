import { Component, OnInit } from '@angular/core';
import { Curso } from './curso';
import { CursoService } from './curso.service';
import { SocialAuthService, SocialUser } from "angularx-social-login";
import { Router } from '@angular/router';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent implements OnInit {

  title:string="Lista de cursos";
  cursos: Curso[] | undefined;

  user: SocialUser | undefined;
  loggedIn: boolean | undefined;
  url: string | undefined;

  constructor(private cursoService:CursoService,
    private authService: SocialAuthService,private router: Router) { }

  ngOnInit(): void {
    console.log(this.cursos);
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
    });

    /*this.cursoService.get(this.router.url.split('/')[0]).subscribe(
      e => this.cursos=e
    );*/

    this.cursoService.getAll().subscribe(
      e => this.cursos=e
    );

    this.url = this.router.url;
    console.log(this.router.url);
  }

  delete(curso:Curso): void{
    console.log("Delete curso con ID: "+curso.id_curso);
    this.cursoService.delete(curso.id_curso!).subscribe(
      res=>this.cursoService.getAll().subscribe(
        response=>this.cursos=response
      )
    );
    
  }

}
