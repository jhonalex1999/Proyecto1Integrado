import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovParabolicoComponent } from './mov-parabolico.component';

const routes: Routes = [{ path: '', component: MovParabolicoComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovParabolicoRoutingModule { }
