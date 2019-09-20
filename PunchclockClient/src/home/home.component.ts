import { Component, OnInit } from '@angular/core';
import { HttpService } from '../services/http.service';
import { Router } from '@angular/router';
import { User } from '../casting/user';
import { CookieService } from 'ngx-cookie-service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public doLoad: boolean;
  public user: User;

  constructor(private httpService: HttpService, private router: Router, private cookieService: CookieService) { }

  ngOnInit() {
    this.httpService.checkJWTValid().subscribe(
      () => this.doLoad = true,
      () => this.router.navigateByUrl('authenticate'));

    this.httpService.getCurrentUser().subscribe(user => this.user = user);
  }

  public signOut() {
    const date = new Date();
    const formattedDate = formatDate(date, 'dd-MM-yyyy hh:mm:ss a', 'en-US', '+02');
    this.cookieService.delete(this.httpService.jwtKey);
    this.navigateTo('/authenticate');
    // this.httpService.logCheckOutTime(formattedDate).subscribe(() => {
    //   this.cookieService.delete(this.httpService.jwtKey);
    //   this.navigateTo('/authenticate');
    // });
  }

  public changeUser() {
    this.navigateTo('/profile');
  }

  public deleteUser() {
    this.httpService.deleteCurrentUser().subscribe(() => this.navigateTo('')); // todo
  }

  private navigateTo(route: string) {
    this.router.navigateByUrl(route);
  }
}
