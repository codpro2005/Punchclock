import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { User } from '../casting/user';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { Entry } from 'src/casting/entry';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  private apiURL = 'http://localhost:8081/';
  public jwtKey = 'Authorization';
  public jwt: string;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  public createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.apiURL}users/sign-up`, user);
  }

  public getJWT(user: User): Observable<HttpResponse<object>> {
    return this.httpClient.post(`${this.apiURL}login`, user, { observe: 'response' });
  }

  public checkJWTValid(): Observable<boolean> {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.get<boolean>(`${this.apiURL}users/valid`, { headers: contentHeader });
  }

  public getCurrentUser(): Observable<User> {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.get<User>(`${this.apiURL}users/current`, { headers: contentHeader });
  }

  public updateCurrentUser(newUser: User): Observable<User> {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.post<User>(`${this.apiURL}users/update`, newUser, { headers: contentHeader });
  }

  public deleteCurrentUser() {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.get(`${this.apiURL}users/delete`, { headers: contentHeader });
  }

  public showCurrentEntries() {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.get<Entry[]>(`${this.apiURL}entries/current`, { headers: contentHeader });
  }

  public logCheckInTime() {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.post(`${this.apiURL}entries/checkIn`, {} , { headers: contentHeader });
  }

  public logCheckOutTime() {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.post(`${this.apiURL}entries/checkOut`, {}, { headers: contentHeader });
  }

  public updateEntryCheckIn(entry: any): Observable<Entry> {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.post<Entry>(`${this.apiURL}entries/checkIn/update`, entry, { headers: contentHeader });
  }

  public updateEntryCheckOut(entry: any): Observable<Entry> {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.post<Entry>(`${this.apiURL}entries/checkOut/update`, entry, { headers: contentHeader });
  }

  public deleteAllCurrentUserEntries() {
    const contentHeader = this.getAuthorizationHeader();
    return this.httpClient.get(`${this.apiURL}entries/current/delete`, { headers: contentHeader });
  }

  private getAuthorizationHeader(): HttpHeaders {
    return new HttpHeaders({ Authorization: this.cookieService.get(this.jwtKey), 'Content-Type': 'application/json' });
  }
}
