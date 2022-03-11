import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from './usuario';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  usuarioNuevo: Usuario = new Usuario();

  constructor(private usuarioService: UsuarioService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
  }

  create(): void{
    this.usuarioService.create(this.usuarioNuevo).subscribe(
      res => this.router.navigate([this.router.url.split("/")[1] + '/cursos'])
    );
  }

}
