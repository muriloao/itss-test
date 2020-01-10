import { Component, OnInit } from '@angular/core';
import { Customer } from './customer.model';
import { FormGroup, FormControl } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { CustomerService } from './customer.service';
import { Form } from '../shared/form';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent extends Form implements OnInit {
  customers: Customer[] = [];
  editId: number = 0;
  cols = [
    { field: 'name', header: 'Nome' },
    { field: 'cpf', header: 'CPF' },
    { field: 'phone', header: 'Telefone' },
  ];

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
    private customerService: CustomerService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { super() }

  ngOnInit() {
    this.update();
  }
  update() {
    this.customers = [];
    this.customerService.fetchAll().subscribe(data => {
      console.log('Response');
      console.log(data);
      data.content.forEach(customer => {
        this.customers.push(customer);
      });
    })
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
    if (!this.editId)
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
    else
      this.customerService.update(this.editId, payload).subscribe((data: Customer) => {
        // sucess
        console.log('Response');
        console.log(data);
        this.customerForm.reset();
        super.addSuccessMessage('Cliente atualizado com sucesso');
        console.log(this.customers);
        this.update();
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
  modalEdit(customer: Customer) {
    this.editId = customer.id;
    this.customerForm.controls.name.setValue(customer.name);
    this.customerForm.controls.cpf.setValue(customer.cpf);
    this.customerForm.controls.phone.setValue(customer.phone);
  }
  cancelEdit() {
    this.editId = 0;
    this.customerForm.reset();
  }
  modalDelete(customer: Customer) {
    this.confirmationService.confirm({
      message: `Deseja excluir ${customer.name} ? não será possível recuperar`,
      accept: () => {
        return this.delete(customer.id);
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
