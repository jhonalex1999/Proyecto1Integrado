import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private url:string="http://localhost:8080/usuario";

  constructor(private http:HttpClient) { }
  
  //Crear Usuario
  create(usuario:Usuario):Observable<Usuario>{
    return this.http.post<Usuario>(this.url+'/add', usuario);
  }

  //Obtener un Usuario
  get(id:string):Observable<Usuario>{
    return this.http.get<Usuario>(this.url+'/list/'+id);
  }

  //Obtener Usuarios
  getAll():Observable<Usuario[]>{
    return this.http.get<Usuario[]>(this.url+'/list');
  }

  //Obtener Estudiantes de un Curso
  getEstudiantes(id:string):Observable<Usuario[]>{
    return this.http.get<Usuario[]>(this.url+'/listestcurso/'+id);
  }

  //Update Usuario
  update(id:string,usuario:Usuario):Observable<Usuario>{
    return this.http.put<Usuario>(this.url+'/update'+'/'+id,usuario);
  }

  //Eliminar Usuario
  delete(id:string):Observable<Usuario>{
    return this.http.delete<Usuario>(this.url+'/delete/'+id);
  }
}
