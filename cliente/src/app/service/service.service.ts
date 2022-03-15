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

  public obtenerUser(){
    return this.auth.currentUser!;
  }
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

//return this.httpClient.get(`${this.API_BASE}/pdf`).subscribe(result => this.data = result);
  logout() {
    this.cookie.delete('Token_access', '')
    this.cookie.delete('Token_email', '')
    this.cookie.delete('Token_name', '')
    this.cookie.delete('Token_photo', '')
    try {
      this.cambiarEstadoSalida();
      this.auth.signOut();
    } catch (error) {
      console.log(error);
    }
  }

  cambiarEstadoEntrada() {
    console.log("llegue");
    return this.httpClient.get(`${this.API_BASE}/` + this.cookie.get('Token_email') + `/` + `cambiarEstadoParticipanteEntrada`).subscribe(result => this.data = result);
  }

  cambiarEstadoSalida() {
    console.log("sali");
    return this.httpClient.get(`${this.API_BASE}/` + this.cookie.get('Token_email') + `/` + `cambiarEstadoParticipanteSalida`).subscribe(result => this.data = result);
  }

  enviarDatos() {
    console.log("Entro a enviarDatos()");
    //return this.httpClient.post(`${this.API_BASE}/`+this.logeado.email+ `/` +this.logeado.displayName+ `/ingresarUsuario`,this.logeado);
    return this.httpClient.post(`${this.API_BASE}/` + this.logeado.email + `/` + this.logeado.displayName + `/ingresarUsuario`, this.logeado).subscribe(result => this.data = result);
  }


  descargar( codigo_planta: number) {
    console.log('Entro a descargarPDF')
    return this.httpClient.get(`${this.API_BASE_LAB}/`+codigo_planta+`/`+`descargarDatos`);

  }

  descargarGuia( codigo_planta: number){
    console.log("Entro a descargarGuia()");
    return this.httpClient.get(`${this.API_BASE_PRACTICA}/`+codigo_planta+`/` + `descargarArchivoProfesor`);
  }


  enviarIntegrantes(eventFranja: number, arrayIntergrantes: any[]): Observable<number> {
    console.log("Entro a enviarIntegrantes");
    return this.httpClient.post<number>(`${this.API_BASE_PRACTICA}/` + eventFranja + `/` + `agregarParticipantes`, arrayIntergrantes);


  }

  agendamiento(codigo_laboratorio: number): Observable<Agendamiento[]> {
    console.log("Entro a Agendamiento");
    return this.httpClient.get<Agendamiento[]>(`${this.API_BASE_PRACTICA}/` + codigo_laboratorio + `/listarAgendamiento`);
  }

  codigos(codigo_materia: any) {
    console.log("Entro a matricular curso()");
    console.log(codigo_materia);
    return this.httpClient.post(`${this.API_BASE}/` + this.cookie.get('Token_email') + `/` + codigo_materia + `/` + `matricularCurso`, codigo_materia);
  }

  verCursosMatriculados() {
    console.log("Entro a vercursos");
    //console.log(this.cookie.get('Token_email'));
    return this.httpClient.get(`${this.API_BASE}/` + this.cookie.get('Token_email') + `/` + `buscarCursosMatriculados`);
  }

  saberRol() {
    console.log("Entro a saberrol");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + this.cookie.get('Token_email') + `/` + `buscarQuienEsLider`, { responseType: 'text' });
  }

  saberCodigoGrupo() {
    return this.httpClient.get(`${this.API_BASE_LAB}/` + this.cookie.get('Token_email') + `/` + `saberCodigoGrupo`, { responseType: 'text' });
  }

  verificarAgendamientoGrupo(codigo: any, codigo_planta: number): Observable<Boolean> {
    console.log("Entro a verificarAgendamiento");
    return this.httpClient.get<Boolean>(`${this.API_BASE_PRACTICA}/` + codigo + `/` + codigo_planta + `/` + `verificarAgendamiento`);
  }
  verificarGrupoCompleto(codigo: any) {
    console.log("Entro a verificargrupos");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo + `/` + `buscarCompletitudEstudiantes`);
  }

  finalizarPractica(codigo_grupo: any) {
    console.log("Entro a finalizar practica");
    return this.httpClient.delete(`${this.API_BASE_LAB}/` + codigo_grupo + `/` + `finalizarPractica`);
  }

  finalizarSimulacion(cod_lab: any) {
    console.log("Entro a finalizar simulacion");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + cod_lab + `/` + `finalizarProceso`);
  }

  obtenerOpcionesCL_Repeticiones(codigo_planta: number) {
    console.log("Entro a obtener opciones repeticiones");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Altura_CL`);
  }/* 
  obtenerOpcionesLH_Elongacion(codigo_planta: number) {
    console.log("Entro a obtener opciones practica caida libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Elongacion_LH`);
  }
  obtenerOpcionesLH_Fuerza(codigo_planta: number) {
    console.log("Entro a obtener opciones practica caida libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Fuerza_LH`);
  }*/
  obtenerOpcionesLH_Pesos(codigo_planta: number) {
    console.log("Entro a obtener opcionesLey Hooke");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Pesos_LH`);
  }

  obtenerOpcionesMP_Angulo(codigo_planta: number) {
    console.log("Entro a obtener opciones practica caida libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Angulo_MP`);
  }
  obtenerOpcionesMP_Velocidad(codigo_planta: number) {
    console.log("Entro a obtener opciones practica caida libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo_planta + `/` + `listar_Velocidad_MP`);
  }

  InicioLeyHooke(peso: number) {
    console.log("Entro a InicioLeyHooke");
    return this.httpClient.get<Boolean>(`${this.API_BASE_LAB}/`+ peso+`/` + `iniciarLeyHooke`);
  }
  
  InicioCaidaLibre(peso: number) {
    console.log("Entro a InicioCaidaLibre");
    return this.httpClient.get<Boolean>(`${this.API_BASE_LAB}/` + peso +`/` + `iniciarCaidaLibre`);
  }
  
  InicioMovParabolico(angulo: number,velocidad: number) {
    console.log("Entro a InicioMovParabolico");
    return this.httpClient.get<Boolean>(`${this.API_BASE_LAB}/` + angulo + `/`+ velocidad+`/` + `iniciarMovimientoParabolico`);
  }

  obtenerDatosCLAltura(codigo:any){
    console.log("Entro a obtener datos Caida Libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarAltura`);
  }

  obtenerDatosCLTiempo(codigo:any){
    console.log("Entro a obtener tiempo Caida Libre");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarTiempo`);
  }
  
  obtenerDatosLHElongaciones(codigo:any){
    console.log("Entro a obtener Elongacion Ley Hooke");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarElongaciones`);
  }

  obtenerDatosLHPesos(codigo:any){
    console.log("Entro a obtener Pesos Ley Hooke");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarPesos`);
  }

  obtenerDatosMPX(codigo:any){
    console.log("Entro a obtener X Mov Parabolico");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarX`);
  }

  obtenerDatosMPY(codigo:any){
    console.log("Entro a obtener Y Mov Parabolico");
    return this.httpClient.get(`${this.API_BASE_LAB}/` + codigo +`/retornarY`);
  }


  duracionPractica(codigo: any, codigo_planta: number): Observable<number> {
    console.log("Entro a duracionPractica");
    return this.httpClient.get<number>(`${this.API_BASE_PRACTICA}/` + codigo + `/` + codigo_planta + `/` + `duracion`);
  }

  obtenerTipo(){
    console.log("Entro a obtenerTipo");
    return this.httpClient.get(`${this.API_BASE}/`+ this.cookie.get('Token_email') + `/`  +`sacarRol`,{responseType:'text'});
  }
}