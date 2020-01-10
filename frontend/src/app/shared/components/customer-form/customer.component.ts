import { Component, OnInit, Input, Output } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Customer } from '../../../customer-page/customer.model';
import { Form } from 'src/app/shared/form';
import { CustomerService } from 'src/app/customer-page/customer.service';

@Component({
  selector: 'app-customer-form',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerFormComponent extends Form implements OnInit {

  @Input() customers: Customer[];

  customerForm: FormGroup = new FormGroup({
    name: new FormControl(),
    cpf: new FormControl(),
    phone: new FormControl()
  });

  errors = {
    name: '',
    cpf: '',
    phone: ''
  };

  constructor(
    private customerService: CustomerService
  ) {
    super();
  }

  ngOnInit() {
  }

  validate() {
    this.reset();
    let pass = true;
    if (!this.customerForm.controls.name.value) {
      this.errors.name = 'Informe o nome';
      pass = false;
    }
    if ((!this.customerForm.controls.cpf.value) || this.customerForm.controls.cpf.value == 0) {
      this.errors.cpf = 'Informe o CPF';
      pass = false;
    }
    if ((!this.customerForm.controls.phone.value) || this.customerForm.controls.phone.value == 0) {
      this.errors.phone = 'Informe o telefone';
      pass = false;
    }
    return pass;
  }

  reset() {
    this.errors = {
      name: '',
      cpf: '',
      phone: ''
    };
  }

  register() {
    if (!this.validate()) {
      return;
    }
    this.reset();

    let payload: Customer = new Customer();
    payload.name = this.customerForm.controls.name.value;
    payload.cpf = this.customerForm.controls.cpf.value;
    payload.phone = this.customerForm.controls.phone.value;
    this.customerService.register(payload).subscribe((data: Customer) => {
      // sucess
      console.log('Response');
      console.log(data);
      this.customerForm.reset();
      super.addSuccessMessage('Cliente registrado com sucesso');
      console.log(this.customers);
      this.customers.push(data);
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
