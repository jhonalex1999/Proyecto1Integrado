import { Injectable } from '@angular/core';
import { Auth, signInWithEmailAndPassword } from '@angular/fire/auth';
import { createUserWithEmailAndPassword, getAdditionalUserInfo, getAuth, GoogleAuthProvider, signInWithPopup, signOut, User, UserCredential } from 'firebase/auth';
import { first, Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Agendamiento } from '../moduloEstudiante/modelos/agendamiento';
//import { LoginData } from '../interfaces/login-data';


@Injectable({
  providedIn: 'root'
})

export class AuthService {
  data = {};
  public listado : any = [];

  private API_BASE = 'http://localhost:8080/usuario';
  private API_BASE_LAB = 'http://localhost:8080/laboratorio';
  private API_BASE_PRACTICA = 'http://localhost:8080/practica';

  logeado: import("@angular/fire/auth").User;

  public eventosQuemados: any = [];

  bandera: Boolean = false; 
  rol: String = "";
  //var bandera$ : Boolean = false; 
  //correo = signInWithPopup(this.auth, new GoogleAuthProvider());
  

  constructor(private auth: Auth, private httpClient: HttpClient, private cookie: CookieService) { }


  async loginWithGoogle() {
    try {
      var correo = signInWithPopup(this.auth, new GoogleAuthProvider());
      this.logeado = (await correo).user;
      var idx = (await correo).user?.email?.indexOf('@unicauca.edu.co');
      if (Number(idx) > -1) {
        //alert("Bienvenido " + (await correo).user?.displayName);
        this.enviarDatos();
        return await correo;
      }
      alert("Error correo no universitario");
      return null;
    } catch (error) {
      console.log(error);
      return null;
    }

  }

  enviarDatos() {
    console.log("Entro a enviarDatos()");
    //return this.httpClient.post(`${this.API_BASE}/`+this.logeado.email+ `/` +this.logeado.displayName+ `/ingresarUsuario`,this.logeado);
    return this.httpClient.post(`${this.API_BASE}/`+ this.logeado.email + `/` + this.logeado.displayName + `/ingresarUsuario`,this.logeado).subscribe(result => this.data = result);
  }

//return this.httpClient.get(`${this.API_BASE}/pdf`).subscribe(result => this.data = result);
  logout() {
    try {
      this.auth.signOut();
    } catch (error) {
      console.log(error);
    }
  }

  /*descargar() {
    console.log('Descargó')
    //return this.httpClient.get(`${this.API_BASE}/pdf`).subscribe(result => this.data = result);

  }*/

  traerrol() {
    console.log('rol');
    //return this.httpClient.get(`${this.API_BASE}/rol`).subscribe(result => this.data = result);
  }

 

  enviarIntegrantes(eventFranja: number, arrayIntergrantes: any[]) : Observable<number>{
    console.log("Entro a enviarIntegrantes");
    return this.httpClient.post<number>(`${this.API_BASE_LAB}/` + eventFranja + `/` + `agregarParticipantes`, arrayIntergrantes);
    
    
  }

  agendamiento(codigo_laboratorio : number): Observable<Agendamiento[]> {
    console.log("Entro a Agendamiento");
    return this.httpClient.get<Agendamiento[]>(`${this.API_BASE_LAB}/`+ codigo_laboratorio + `/listarAgendamiento`);}
  codigos(codigo_materia:any) {
    console.log("Entro a matricular curso()");
    this.httpClient.post(`${this.API_BASE}/`+ this.cookie.get('Token_email') + `/` + codigo_materia + `/` + `matricularCurso`,codigo_materia).subscribe((result:any)=>{this.bandera=result});
    //this.verificarmateria();
    //this.verCursosMatriculados();
    this.bandera;
  }

  verCursosMatriculados(){
    console.log("Entro a vercursos");
    //console.log(this.cookie.get('Token_email'));
    return this.httpClient.get(`${this.API_BASE}/`+ this.cookie.get('Token_email') + `/` + `buscarCursosMatriculados`);
  }

  saberRol(){
    console.log("Entro a saberrol");
    return this.httpClient.get(`${this.API_BASE_LAB}/`+ this.cookie.get('Token_email') + `/` + `buscarQuienEsLider`,{responseType:'text'});
  }

  saberCodigoGrupo(){
    return this.httpClient.get(`${this.API_BASE_LAB}/`+ this.cookie.get('Token_email') + `/` + `saberCodigoGrupo`,{responseType:'text'});
  }

  verificarAgendamientoGrupo(codigo:any) : Observable<Boolean>{
    console.log("Entro a verificarAgendamiento");
    return this.httpClient.get<Boolean>(`${this.API_BASE_PRACTICA}/`+ codigo + `/` + `verificarAgendamiento`);
  }
  verificarGrupoCompleto(codigo:any){
    console.log("Entro a verificargrupos");
    return this.httpClient.get(`${this.API_BASE_LAB}/`+ codigo + `/` + `buscarCompletitudEstudiantes`);
  }
 
}