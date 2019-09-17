import { Component, OnInit } from '@angular/core';
import { AuthenticationPath } from 'src/casting/AuthenticationPath';
import { Router } from '@angular/router';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.scss']
})
export class AuthenticateComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    this.setAuthenticationOption(AuthenticationPath.SignIn);
  }

  public checkNavigation(navigationCheck: string): boolean {
    return navigationCheck === this.router.url;
  }

  public setAuthenticationOption(authenticationOption: AuthenticationPath) {
    this.changeNavigation(authenticationOption);
  }

  private changeNavigation(navigationUrl: string) {
    this.router.navigateByUrl(navigationUrl);
  }
}
