import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from 'src/app/usuario/usuario';
import { UsuarioService } from 'src/app/usuario/usuario.service';

@Component({
  selector: 'app-estudiante',
  templateUrl: './estudiante.component.html',
  styleUrls: ['./estudiante.component.scss']
})
export class EstudianteComponent implements OnInit {

  public estudiantes: Usuario[];
  public urlBack: string;

  constructor(private usuarioService: UsuarioService, private router: Router) { }

  ngOnInit(): void {
    this.urlBack = this.router.url.split('/materia')[0]+'/materia'
    this.usuarioService.getEstudiantes(this.router.url.split('/')[5]).subscribe(
      res => this.estudiantes = res
    );
  }

}
