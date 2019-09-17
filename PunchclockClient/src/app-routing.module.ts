import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { AppComponent } from './app.component';
import { AuthenticateComponent } from './authenticate/authenticate.component';
import { SignInComponent } from './authenticate/sign-in/sign-in.component';
import { SignUpComponent } from './authenticate/sign-up/sign-up.component';

const routes: Routes = [
  { path: '', redirectTo: 'authenticate', pathMatch: 'full' },
  { path: 'authenticate', component: AuthenticateComponent, canActivate: [AuthGuard], children: [
    { path: 'sign-in', component: SignInComponent },
    { path: 'sign-up', component: SignUpComponent }
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
