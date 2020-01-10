import { Component, OnInit } from '@angular/core';
import { CarService } from './car.service';
import { CustomerService } from '../customer-page/customer.service';
import { Form } from '../shared/form';
import { FormGroup, FormControl } from '@angular/forms';
import { Car } from './car.model';
import { Customer } from '../customer-page/customer.model';
import { ConfirmationService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent extends Form implements OnInit {
  cols = [
    { field: 'board', header: 'Placa' },
    { field: 'color', header: 'Cor' },
    { field: 'model', header: 'Modelo' },
  ];
  carForm: FormGroup = new FormGroup({
    board: new FormControl(),
    model: new FormControl(),
    color: new FormControl(),
    customer: new FormControl()
  });

  customers: any = [];
  cars: Car[] = [];


  errors = {
    board: '',
    model: '',
    color: '',
    customer: ''
  };


  editId: number = 0;
  constructor(
    private carService: CarService,
    private customerService: CustomerService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { super() }

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
    this.update();
  }
  update() {
    this.cars = [];
    this.carService.fetchAll().subscribe(data => {
      data.content.forEach(car => {
        this.cars.push(car);
      });
    })
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
    if (!this.editId)
      this.carService.register(payload).subscribe((data: Car) => {
        // sucess
        console.log('Response');
        console.log(data);
        this.carForm.reset();
        this.addSuccessMessage('Veículo registrado');
        this.cars.push(data);

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
    else
      this.carService.update(this.editId, payload).subscribe((data: Car) => {
        // sucess
        console.log('Response');
        console.log(data);
        this.carForm.reset();
        this.addSuccessMessage('Veículo atualizado');
        this.update()

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
  modalEdit(car: Car) {
    this.editId = car.id;
    this.carForm.controls.board.setValue(car.board);
    this.carForm.controls.model.setValue(car.model);
    this.carForm.controls.color.setValue(car.color);
  }
  cancelEdit() {
    this.editId = 0;
    this.carForm.reset();
  }
  modalDelete(car: Car) {
    this.confirmationService.confirm({
      message: `Deseja excluir ${car.board} ? não será possível recuperar`,
      accept: () => {
        return this.delete(car.id);
      }
    });
  }

  delete(id: number) {
    console.log('id ' + id);
    this.customerService.delete(id).subscribe(data => {
      // deletado
      console.log('Response');
      console.log(data);
      this.update();
    })
  }
}
