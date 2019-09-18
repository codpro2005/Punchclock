import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { User } from '../casting/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  apiURL = 'http://localhost:8081/';

  constructor(private httpClient: HttpClient) { }

  public createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.apiURL}users/sign-up`, user);
  }

  public getJWT(user: User): Observable<HttpResponse<object>> {
    const contentHeader = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post(`${this.apiURL}login`, user, { headers: contentHeader, observe: 'response' });
  }
}
