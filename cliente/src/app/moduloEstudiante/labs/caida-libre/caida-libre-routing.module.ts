import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CaidaLibreComponent } from './caida-libre.component';

const routes: Routes = [{ path: '', component: CaidaLibreComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CaidaLibreRoutingModule { }
