import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { AppComponent } from './app.component';
import { AuthenticateComponent } from './authenticate/authenticate.component';

const routes: Routes = [
  { path: '', redirectTo: 'authenticate', pathMatch: 'full' },
  { path: 'authenticate', component: AuthenticateComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
