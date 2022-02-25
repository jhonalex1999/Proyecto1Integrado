import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LeyHookeComponent } from './ley-hooke.component';

const routes: Routes = [{ path: '', component: LeyHookeComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LeyHookeRoutingModule { }
