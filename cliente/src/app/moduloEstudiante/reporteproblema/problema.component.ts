import { Component, Inject, OnInit } from '@angular/core';
import {MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AuthService } from 'src/app/service/service.service';

@Component({
  selector: 'app-problema',
  templateUrl: './problema.component.html',
  styleUrls: ['./problema.component.scss']
})
export class ProblemaComponent implements OnInit {

  constructor(public dialogRef:MatDialogRef<ProblemaComponent>,public authService: AuthService,@Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {
  }

  enviarcodigo(data:any){
    this.authService.reportar_error(1,data);
  }

}
