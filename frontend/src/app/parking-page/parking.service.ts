import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Parking } from './parking.model';
import { GenericService } from '../shared/service/generic-service';

@Injectable({
    providedIn: 'root'
})
export class ParkingService extends GenericService<Parking> {

    constructor(http: HttpClient) {
        super('/parking', http);
    }
}
