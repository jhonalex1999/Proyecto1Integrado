import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from './usuario';

@Injectable({
    providedIn: 'root'
  })

export class Usuarioservice {

    private url:string="http://localhost:8001/usuario";

    constructor(private http:HttpClient) { }

  //Obtener Usuarios
  getAll():Observable<Usuario[]>{
    return this.http.get<Usuario[]>(this.url+'/list');
  }

  //Crear Usuarios
  create(usuario:Usuario):Observable<Usuario>{
    return this.http.post<Usuario>(this.url+'/add', usuario);
  }

  //Obtener un Usuario
  get(id:string):Observable<Usuario>{
    return this.http.get<Usuario>(this.url+'/list/'+id);
  }

  //Update Usuarios
  update(usuario:Usuario):Observable<Usuario>{
    console.log(usuario.idUsuario);
    return this.http.put<Usuario>(this.url+'/update'+'/'+usuario.idUsuario,usuario);
  }

  //Eliminar Usuarios
  delete(id:string):Observable<Usuario>{
    return this.http.delete<Usuario>(this.url+'/delete/'+id);
  }
}
