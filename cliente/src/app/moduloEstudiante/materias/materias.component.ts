import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-materias',
  templateUrl: './materias.component.html',
  styleUrls: ['./materias.component.scss']
})
export class MateriasComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  bandera: Boolean = false;

  verificarAgendamiento() {
    this.authService.saberCodigoGrupo().subscribe(respuesta=> {this.authService.verificarAgendamientoGrupo(respuesta).pipe(finalize(() => this.prueba())).subscribe((result: any) => { this.bandera=result })
  });
  }

  prueba(){
    if (this.bandera) {
      this.router.navigate(['/caida-libre']);
    } else {
      alert('No tienes practicas agendadas en este horario');
  
    }
  }
}
