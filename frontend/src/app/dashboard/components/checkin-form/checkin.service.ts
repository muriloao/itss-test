import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Checkin } from './checkin.model';
import { GenericService } from 'src/app/shared/service/generic-service';

@Injectable({
  providedIn: 'root'
})
export class CheckinService extends GenericService<Checkin> {

  constructor(http: HttpClient) { super('/parking/checkin', http) }

  registerCheckin(car: Checkin): Observable<Checkin> {
    console.log('Request ' + this.baseUrl + " " + JSON.stringify(car));
    return this.http.post<any>(this.baseUrl, car, this.options);
  }
}
