import { Component, OnInit } from '@angular/core';
import { CarService } from '../../../car-page/car.service';
import { FormGroup, FormControl } from '@angular/forms';
import { Form } from 'src/app/shared/form';
import { CheckoutFormService } from './checkout.service';

@Component({
  selector: 'app-checkout-form',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutFormComponent extends Form implements OnInit {
  cars: Array<any> = [];
  checkoutForm: FormGroup = new FormGroup({
    car: new FormControl(),
  });
  errors = {
    car: '',
  };
  constructor(
    private checkoutService: CheckoutFormService,
    private carService: CarService,
  ) {
    super();
  }

  ngOnInit() {
    this.carService.fetchByStatus('IN').subscribe((data) => {
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
    if (!this.checkoutForm.controls.car.value) {
      this.errors.car = 'Selecione o veículo';
      pass = false;
    }
    return pass;
  }

  reset() {
    this.errors = {
      car: '',
    };
  }

  registerCheckout() {
    if (!this.validate()) {
      return;
    }
    this.reset();
    this.checkoutService.registerCheckout(this.checkoutForm.controls.car.value[0]).subscribe((data: any) => {
      // sucess
      console.log('Response');
      console.log(data);
      this.checkoutForm.reset();
      super.addSuccessMessage(`Horas: ${data.time}, Valor: ${data.totalPrice}`);
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
