import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Curso } from './curso';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  private url:string="http://localhost:8080/curso";

  constructor(private http:HttpClient) { }

  //Obtener Cursos
  getAll():Observable<Curso[]>{
    return this.http.get<Curso[]>(this.url+'/list');
  }

  //Obtener Cursos
  getAllById(id:string):Observable<Curso[]>{
    return this.http.get<Curso[]>(this.url+'/listbydocente/'+id);
  }

  //Crear curso
  create(curso:Curso):Observable<Curso>{
    return this.http.post<Curso>(this.url+'/add', curso);
  }

  //Obtener un Curso
  get(id:string):Observable<Curso>{
    return this.http.get<Curso>(this.url+'/list/'+id);
  }

  //Update Curso
  update(id:string,curso:Curso):Observable<Curso>{
    return this.http.put<Curso>(this.url+'/update'+'/'+id,curso);
  }

  //Eliminar Curso
  delete(id:string):Observable<Curso>{
    return this.http.delete<Curso>(this.url+'/delete/'+id);
  }
}
