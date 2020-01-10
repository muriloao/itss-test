import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GenericService } from 'src/app/shared/service/generic-service';

@Injectable({
  providedIn: 'root'
})
export class CheckoutFormService extends GenericService<any> {

  constructor(http: HttpClient) { super('/parking/checkout', http) }

  registerCheckout(id: number): Observable<any> {
    console.log(`Request ${this.baseUrl}/${id}`);
    return this.http.put<any>(`${this.baseUrl}/${id}`, {}, this.options);
  }
}
