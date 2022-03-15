import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from './usuario';
import { UsuarioService } from './usuario.service';

export interface UsuarioDTO {
  correo: string;
  id_usuario: number;
  rol: number;
  nombre_completo: string;
}


@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss']
})
export class UsuarioComponent implements OnInit {

  displayedColumns: string[] = ['correo', 'nombre_completo', 'rol'];
  usuarioNuevo: Usuario = new Usuario();
  dataSource = new MatTableDataSource();
  accion: string = "crear";

  constructor(private usuarioService: UsuarioService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.usuarioService.getAll().subscribe(
      res => this.dataSource = new MatTableDataSource(res)
    );

  }

  create(): void {
    this.usuarioService.create(this.usuarioNuevo).subscribe(
      res => this.ngOnInit()
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  delete(id: string): void {
    this.usuarioService.delete(id).subscribe(
      res => this.ngOnInit()
    );
  }

  cargar(usuarioActualizar: any): void {
    this.usuarioNuevo = usuarioActualizar;
    this.accion = "actualizar"
  }

  update(): void {
    this.accion = "crear"
    this.usuarioService.update(this.usuarioNuevo.id_usuario, this.usuarioNuevo).subscribe(
      res => this.ngOnInit()
    )
    this.usuarioNuevo = new Usuario();
  }
}
