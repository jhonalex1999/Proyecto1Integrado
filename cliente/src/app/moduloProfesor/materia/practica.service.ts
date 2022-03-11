import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Practica } from './practica';

@Injectable({
  providedIn: 'root'
})
export class PracticaService {

  private url:string="http://localhost:8080/practica";

  constructor(private http:HttpClient) { }

  //Obtener practicas
  getAll():Observable<Practica[]>{
    return this.http.get<Practica[]>(this.url+'/list');
  }

  //Crear Practica
  create(practica:Practica):Observable<Practica>{
    console.log(practica);
    return this.http.post<Practica>(this.url+'/add', practica);
  }

  //Obtener un Curso
  get(correo:string):Observable<Practica[]>{
    return this.http.get<Practica[]>(this.url+'/listbyCorreo/'+correo);
  }

  getById(id:string):Observable<Practica>{
    console.log(this.url+'/list/'+id);
    return this.http.get<Practica>(this.url+'/list/'+id);
  }

  //Update Curso
  update(practica:Practica):Observable<Practica>{
    console.log(practica.idpractica);
    return this.http.put<Practica>(this.url+'/update'+'/'+practica.idpractica,practica);
  }

  //Eliminar Curso
  delete(id:string):Observable<Practica>{
    return this.http.delete<Practica>(this.url+'/delete/'+id);
  }
}