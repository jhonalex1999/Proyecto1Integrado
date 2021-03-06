import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { FranjaHoraria } from './franja-horaria';

@Injectable({
  providedIn: 'root'
})
export class FranjaHorariaService {

  private url:string="http://localhost:8080/franja";

  constructor(private http:HttpClient) { }

  //Obtener FranjaHoraria
  getAll():Observable<FranjaHoraria[]>{
    return this.http.get<FranjaHoraria[]>(this.url+'/list');
  }

  //Crear FranjaHoraria
  create(FranjaHoraria:FranjaHoraria):Observable<FranjaHoraria>{
    return this.http.post<FranjaHoraria>(this.url+'/add', FranjaHoraria);
  }

  //Obtener un Curso
  get(id:string):Observable<FranjaHoraria>{
    return this.http.get<FranjaHoraria>(this.url+'/list/'+id);
  }

  //Update Curso
  update(FranjaHoraria:FranjaHoraria):Observable<FranjaHoraria>{
    return this.http.put<FranjaHoraria>(this.url+'/update'+'/'+FranjaHoraria.id_franja_horaria,FranjaHoraria);
  }

  //Eliminar Curso
  delete(id:string):Observable<FranjaHoraria>{
    return this.http.delete<FranjaHoraria>(this.url+'/delete/'+id);
  }
}
