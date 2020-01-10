import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Customer } from './customer.model';
import { GenericService } from '../shared/service/generic-service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService extends GenericService<Customer> {

  constructor(http: HttpClient) {
    super('/customer', http);
  }
}
