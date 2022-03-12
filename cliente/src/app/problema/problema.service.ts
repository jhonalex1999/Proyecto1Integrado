import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Problema } from './problema';

@Injectable({
  providedIn: 'root'
})
export class ProblemaService {
  private url:string="http://localhost:8080/problema";

  constructor(private http:HttpClient) { }
  
  //Crear curso
  create(problema:Problema):Observable<Problema>{
    return this.http.post<Problema>(this.url+'/add', problema);
  }

  //Obtener un Curso
  get():Observable<Problema[]>{
    return this.http.get<Problema[]>(this.url+'/list');
  }

  //Update Curso
  update(id:string,problema:Problema):Observable<Problema>{
    return this.http.put<Problema>(this.url+'/update'+'/'+id,problema);
  }

  //Eliminar Curso
  delete(id:string):Observable<Problema>{
    return this.http.delete<Problema>(this.url+'/delete/'+id);
  }
}
