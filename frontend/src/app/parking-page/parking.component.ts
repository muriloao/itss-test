import { Component, OnInit } from '@angular/core';
import { ParkingService } from './parking.service';
import { Parking } from './parking.model';
import { ConfirmationService } from 'primeng/api';
import { FormGroup, FormControl } from '@angular/forms';
import { Form } from '../shared/form';
import { Router } from '@angular/router';

@Component({
  selector: 'app-parking',
  templateUrl: './parking.component.html',
  styleUrls: ['./parking.component.css']
})
export class ParkingComponent extends Form implements OnInit {

  cols = [
    { field: 'description', header: 'Descrição' },
    { field: 'price', header: 'Preço/hora' },
    { field: 'slots', header: 'Vagas' },
  ];
  parkingForm: FormGroup = new FormGroup({
    description: new FormControl(),
    price: new FormControl(),
    slots: new FormControl()
  });

  errors = {
    description: '',
    price: '',
    slots: ''
  };

  parkings: Parking[] = [];
  editId: number = 0;

  constructor(
    private parkingService: ParkingService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { super() }

  ngOnInit() {
    this.update();
  }

  validate() {
    this.reset();
    let pass = true;
    if (!this.parkingForm.controls.description.value) {
      this.errors.description = 'Informe a descrição';
      pass = false;
    }
    if ((!this.parkingForm.controls.slots.value) || this.parkingForm.controls.slots.value == 0) {
      this.errors.slots = 'Informe a quantidade de vagas';
      pass = false;
    }
    if ((!this.parkingForm.controls.price.value) || this.parkingForm.controls.price.value == 0) {
      this.errors.price = 'Informe o valor por hora';
      pass = false;
    }
    return pass;
  }
  reset() {
    this.errors = {
      description: '',
      slots: '',
      price: ''
    };
  }
  update() {
    this.parkings = [];
    this.parkingService.fetchAll().subscribe(data => {
      console.log(data);
      data.content.forEach(parking => {
        this.parkings.push(parking);
      });
    })
  }
  register() {
    if (!this.validate()) {
      return;
    }
    let payload: Parking = new Parking(
      this.parkingForm.controls.description.value,
      this.parkingForm.controls.slots.value,
      this.parkingForm.controls.price.value,
    );
    if (!this.editId) {
      this.parkingService.register(payload).subscribe((data: Parking) => {
        // sucess
        console.log('Response');
        console.log(data);
        this.parkingForm.reset();
        this.addSuccessMessage('Pátio registrado com sucesso');
        if (this.parkings) {
          this.parkings.push(data);
        }
      }, error => {
        console.log(error);
        console.log(JSON.stringify(error))
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
    } else {
      this.parkingService.update(this.editId, payload).subscribe((data: Parking) => {
        // sucess
        console.log(data);
        this.parkingForm.reset();
        this.addSuccessMessage('Pátio atualizado com sucesso');
        this.update();
        this.editId = null;
      }, error => {
        console.log(error);
        console.log(JSON.stringify(error))
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
  modalEdit(parking: Parking) {
    this.editId = parking.id;
    this.parkingForm.controls.description.setValue(parking.description);
    this.parkingForm.controls.price.setValue(parking.price);
    this.parkingForm.controls.slots.setValue(parking.slots);
  }
  cancelEdit() {
    this.editId = 0;
    this.parkingForm.reset();
  }
  modalDelete(parking: Parking) {
    this.confirmationService.confirm({
      message: `Deseja excluir ${parking.description} ? não será possível recuperar`,
      accept: () => {
        return this.delete(parking.id);
      }
    });
  }

  delete(id: number) {
    console.log('id ' + id);
    this.parkingService.delete(id).subscribe(data => {
      // deletado
      console.log('Response');
      console.log(data);
      this.update();
    })
  }

}
