import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Car } from '../../../car-page/car.model';
import { CarService } from '../../../car-page/car.service';
import { Customer } from '../../../customer-page/customer.model';
import { Form } from 'src/app/shared/form';
import { CustomerService } from 'src/app/customer-page/customer.service';

@Component({
  selector: 'app-car-form',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarFormComponent extends Form implements OnInit {

  carForm: FormGroup = new FormGroup({
    board: new FormControl(),
    model: new FormControl(),
    color: new FormControl(),
    customer: new FormControl()
  });

  customers = [];


  errors = {
    board: '',
    model: '',
    color: '',
    customer: ''
  };

  constructor(
    private carService: CarService,
    private customerService: CustomerService
  ) {
    super();
  }

  ngOnInit() {
    this.customerService.fetchAll().subscribe(data => {
      console.log('Response');
      console.log(data);
      data.content.forEach(customer => {
        this.customers.push({
          label: customer.name,
          value: customer.id
        });
      });

    });
  }
  validate() {
    this.reset();
    let pass = true;
    if (!this.carForm.controls.board.value) {
      this.errors.board = 'Informe a placa';
      pass = false;
    }
    if ((!this.carForm.controls.model.value) || this.carForm.controls.model.value == 0) {
      this.errors.model = 'Informe o modelo';
      pass = false;
    }
    if ((!this.carForm.controls.color.value) || this.carForm.controls.color.value == 0) {
      this.errors.color = 'Informe a cor';
      pass = false;
    }
    if ((!this.carForm.controls.customer.value) || this.carForm.controls.customer.value == 0) {
      this.errors.customer = 'Informe o cliente';
      pass = false;
    }
    return pass;
  }
  reset() {
    this.errors = {
      board: '',
      model: '',
      color: '',
      customer: ''
    };
  }

  register() {

    if (!this.validate()) {
      return;
    }
    this.reset();

    let payload: Car = new Car();
    payload.board = this.carForm.controls.board.value;
    payload.model = this.carForm.controls.model.value;
    payload.color = this.carForm.controls.color.value;
    payload.customer = new Customer(this.carForm.controls.customer.value[0]);
    this.carService.register(payload).subscribe((data: Car) => {
      // sucess
      console.log('Response');
      console.log(data);
      this.carForm.reset();
      this.addSuccessMessage('Veículo registrado');

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
        this.addErrorMessage('Não foi possível registrar');
      }
    });
  }

}
