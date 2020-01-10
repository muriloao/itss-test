import { Component, OnInit } from '@angular/core';
import { Parking } from '../parking-page/parking.model';
import { FormControl, FormGroup } from '@angular/forms';
import { CarService } from '../car-page/car.service';
import { ParkingService } from '../parking-page/parking.service';
import { Checkin } from './components/checkin-form/checkin.model';
import { Car } from '../car-page/car.model';
import { CheckinService } from './components/checkin-form/checkin.service';
import { Form } from '../shared/form';
import { CheckoutFormService } from './components/checkout-form/checkout.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent extends Form implements OnInit {


  parkings: Array<any> = [];
  labelParkings = [];
  checkinForm: FormGroup = new FormGroup({
    car: new FormControl(),
    parking: new FormControl(),
  });
  checkoutForm: FormGroup = new FormGroup({
    car: new FormControl(),
  });
  carsCheckin: Array<any> = [];
  carsCheckout: Array<any> = [];

  errorsIn = {
    car: '',
    parking: '',
  };
  errorsOut = {
    car: '',
  };
  messageOut = {
    type: 'error',
    value: ''
  }


  constructor(
    private dashboardService: ParkingService,
    private carService: CarService,
    private checkinService: CheckinService,
    private checkoutService: CheckoutFormService,
    private router: Router
  ) { super() }

  ngOnInit() {
    this.updateIn();
    this.updateOut();

    this.dashboardService.fetchAll().subscribe(data => {
      console.log('Response');
      console.log((data));
      this.parkings = [];
      data.content.forEach((parking: Parking) => {
        this.labelParkings.push({
          label: parking.description,
          value: parking.id
        });
        this.parkings.push({
          ...parking,
          chart: {
            labels: ['Ocupado', 'Livre'],
            datasets: [
              {
                data: [parking.quantity, parking.slots - parking.quantity],
                backgroundColor: [
                  "#FF6384",
                  "#FFCE56"
                ],
                hoverBackgroundColor: [
                  "#FF6384",
                  "#FFCE56"
                ]
              }]
          }
        });
      }, error =>{
        console.log(error.error.text);
      });
    });
  }
  updateIn() {
    this.carsCheckin = [];
    this.carService.fetchByStatus("OUT").subscribe((data) => {
      console.log('Response');
      console.log(data);
      data.forEach(car => {
        this.carsCheckin.push({
          label: `${car.board} - ${car.customer.name}`,
          value: car.id,
        });
      });
    });
  }
  updateOut() {
    this.carsCheckout = [];
    this.carService.fetchByStatus('IN').subscribe((data) => {
      console.log('Response');
      console.log(data);
      data.forEach(car => {
        this.carsCheckout.push({
          label: `${car.board} - ${car.customer.name}`,
          value: car.id,
        });
      });
    })
  }

  validateIn() {
    this.reset();
    let pass = true;
    if (!this.checkinForm.controls.car.value) {
      this.errorsIn.car = 'Selecione o veículo';
      pass = false;
    }
    if ((!this.checkinForm.controls.parking.value)) {
      this.errorsIn.parking = 'Selecione o pátio';
      pass = false;
    }
    return pass;
  }
  validateOut() {
    this.reset();
    let pass = true;
    if (!this.checkoutForm.controls.car.value) {
      this.errorsOut.car = 'Selecione o veículo';
      pass = false;
    }
    return pass;
  }

  reset() {
    this.errorsIn = {
      car: '',
      parking: '',
    };
    this.errorsOut = {
      car: '',
    };
  }

  checkin() {
    if (!this.validateIn()) {
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
      this.updateOut();
    }, error => {
      console.log(error);
      // error
      if (error.status == 400 || error.status == 409) {
        // bad request
        error.error.errors.forEach(err => {
          this.errorsIn[err.field] = err.message;
        });
      } else {
        // error generico
        super.addErrorMessage();
      }
    });
  }
  checkout() {
    if (!this.validateOut()) {
      return;
    }
    this.reset();
    this.checkoutService.registerCheckout(this.checkoutForm.controls.car.value[0]).subscribe((data: any) => {
      // sucess
      console.log('Response');
      console.log(data);
      this.checkoutForm.reset();
      this.messageOut = {
        type: 'success',
        value: `Horas: ${data.time}, Valor: ${data.totalPrice}`
      }
      this.updateIn();
    }, error => {
      console.log(error);
      // error
      if (error.status == 400 || error.status == 409) {
        // bad request
        error.error.errors.forEach(err => {
          this.errorsOut[err.field] = err.message;
        });
      } else {
        // error generico
        this.messageOut = {
          type: 'error',
          value: `Não foi possível registrar`
        }
        super.addErrorMessage();
      }
    });
  }
}
