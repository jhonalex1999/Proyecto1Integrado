import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from 'src/app/service/service.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-vincularmateria',
  templateUrl: './vincularmateria.component.html',
  styleUrls: ['./vincularmateria.component.scss']
})
export class VincularmateriaComponent implements OnInit {

  constructor(public dialogRef:MatDialogRef<VincularmateriaComponent>,public authService: AuthService,
    @Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {
  }

  enviarcodigo(data:any){
    this.authService.codigos(data).subscribe(respuesta => {
      if(respuesta==true){
        console.log("Imprime true");
        window.location.reload();
      }else{
        Swal.fire({
          title: "¡ERROR!",
          text: "Codigo de materia invalido o no encontrado.",
          icon: "error"
        });
      }
    });
    
  }

}
