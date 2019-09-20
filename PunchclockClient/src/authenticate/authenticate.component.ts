import { Component, OnInit } from '@angular/core';
import { AuthenticationPath } from 'src/casting/AuthenticationPath';
import { Router } from '@angular/router';
import { HttpService } from '../services/http.service';

@Component({
  selector: 'app-authenticate',
  templateUrl: './authenticate.component.html',
  styleUrls: ['./authenticate.component.scss']
})
export class AuthenticateComponent implements OnInit {
  public doLoad: boolean;

  constructor(private router: Router, private httpService: HttpService) { }

  ngOnInit() {
    this.httpService.getCurrentUser().subscribe(() => this.router.navigateByUrl(''), () => this.doLoad = true);
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
