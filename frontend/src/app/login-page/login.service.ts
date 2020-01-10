import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GenericService } from '../shared/service/generic-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService extends GenericService<any> {

  constructor(
    http: HttpClient
  ) { super('/auth/oauth/token', http) }

  login(user, password) {
    console.log('Request');
    console.log(`${this.baseUrl}?grant_type=password&username=${user}&password=${password}`);
    return this.http.post<any>(`${this.baseUrl}?grant_type=password&username=${user}&password=${password}`, {}, {
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Authorization": 'Basic bXVyaWxvYW86JDJhJDEwJHA5UGswZlFOQVFTZXNJNHZ1dktBME9aYW5ERDI='
      }
    });
  }

  logout(data) {
    localStorage.setItem('token', '');
  }
}
