import { Component, OnInit, Input } from '@angular/core';
import { Form } from 'src/app/shared/form';
import { FormGroup, FormControl } from '@angular/forms';
import { CheckinService } from './checkin.service';
import { Checkin } from './checkin.model';
import { Car } from '../../../car-page/car.model';
import { CarService } from '../../../car-page/car.service';
import { ParkingService } from '../../../parking-page/parking.service';
import { Parking } from '../../../parking-page/parking.model';

@Component({
  selector: 'app-checkin-form',
  templateUrl: './checkin.component.html',
  styleUrls: ['./checkin.component.css']
})
export class CheckinFormComponent extends Form implements OnInit {

  cars: Array<any> = [];
  @Input() parkings: Array<any>;

  checkinForm: FormGroup = new FormGroup({
    car: new FormControl(),
    parking: new FormControl(),
  });

  errors = {
    car: '',
    parking: '',
  };
  constructor(private checkinService: CheckinService,
    private carService: CarService,
    private parkingService: ParkingService) {
    super();
  }

  ngOnInit() {
    this.carService.fetchByStatus("OUT").subscribe((data) => {
      console.log('Response');
      console.log(data);
      data.forEach(car => {
        this.cars.push({
          label: `${car.board} - ${car.customer.name}`,
          value: car.id,
        });
      });
    })
  }
  validate() {
    this.reset();
    let pass = true;
    if (!this.checkinForm.controls.car.value) {
      this.errors.car = 'Selecione o veículo';
      pass = false;
    }
    if ((!this.checkinForm.controls.parking.value)) {
      this.errors.parking = 'Selecione o pátio';
      pass = false;
    }
    return pass;
  }

  reset() {
    this.errors = {
      car: '',
      parking: '',
    };
  }

  registerCheckin() {
    if (!this.validate()) {
      return;
    }
    this.reset();

    let payload: Checkin = new Checkin();
    payload.car = new Car(this.checkinForm.controls.car.value[0]);
    payload.parking = new Parking(null, null, null, this.checkinForm.controls.parking.value[0]);
    this.checkinService.registerCheckin(payload).subscribe((data: Checkin) => {
      // sucess
      console.log('Response');
      console.log(data);
      this.checkinForm.reset();
      super.addSuccessMessage('Checkin realizado com sucesso');
    }, error => {
      console.log(error);
      // error
      if (error.status == 400 || error.status == 409) {
        // bad request
        error.error.errors.forEach(err => {
          this.errors[err.field] = err.message;
        });
      } else {
        // error generico
        super.addErrorMessage();
      }
    });
  }

}
