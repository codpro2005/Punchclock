import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { User } from '../casting/user';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';

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
    const contentHeader = new HttpHeaders({ Authorization: this.cookieService.get(this.jwtKey) });
    return this.httpClient.get<boolean>(`${this.apiURL}users/valid`, { headers: contentHeader });
  }
}
