import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Car } from './car.model';
import { GenericService } from '../shared/service/generic-service';

@Injectable({
  providedIn: 'root'
})
export class CarService extends GenericService<Car> {

  constructor(http: HttpClient) { super('/car', http); }

  fetchByStatus(status: string) {
    console.log('Request ' + this.baseUrl);
    return this.http.get<any>(this.baseUrl + '/status/' + status, this.options);
  }

}
