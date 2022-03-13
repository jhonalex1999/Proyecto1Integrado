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
  
  //Crear curso
  create(usuario:Usuario):Observable<Usuario>{
    return this.http.post<Usuario>(this.url+'/add', usuario);
  }

  //Obtener un Curso
  get(id:string):Observable<Usuario>{
    return this.http.get<Usuario>(this.url+'/list/'+id);
  }

  //Update Curso
  update(id:string,usuario:Usuario):Observable<Usuario>{
    return this.http.put<Usuario>(this.url+'/update'+'/'+id,usuario);
  }

  //Eliminar Curso
  delete(id:string):Observable<Usuario>{
    return this.http.delete<Usuario>(this.url+'/delete/'+id);
  }
}
