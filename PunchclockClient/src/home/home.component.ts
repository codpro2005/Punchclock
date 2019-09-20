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
  public isChange: boolean;

  constructor(private httpService: HttpService, private router: Router, private cookieService: CookieService) { }

  ngOnInit() {
    this.httpService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.doLoad = true;
    },
    () => this.navigateTo('authenticate'));
  }

  public updateUser(newUser: any) {
    console.log(newUser);
    this.user = newUser;
  }

  public showEntries() {
    this.isChange = false;
    this.navigateTo('entries');
  }

  public signOut() {
  //   const date = new Date();
  //   const formattedDate = formatDate(date, 'dd-MM-yyyy hh:mm:ss a', 'en-US', '+02');
    this.httpService.logCheckOutTime().subscribe(
      () => {
      this.cookieService.delete(this.httpService.jwtKey);
      this.navigateTo('/authenticate');
    },
    () => {
      this.cookieService.delete(this.httpService.jwtKey);
      this.navigateTo('/authenticate');
    });
  }

  public changeUser() {
    this.isChange = true;
  }

  public deleteUser() {
    this.httpService.deleteCurrentUser().subscribe(() => {
      this.cookieService.delete(this.httpService.jwtKey);
      this.navigateTo('/authenticate');
    }); // todo
  }

  private navigateTo(route: string) {
    this.router.navigateByUrl(route);
  }
}
