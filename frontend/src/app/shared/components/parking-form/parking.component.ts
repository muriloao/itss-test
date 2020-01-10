import { Component, OnInit, Input } from '@angular/core';
import { ParkingService } from '../../../parking-page/parking.service';
import { Parking } from '../../../parking-page/parking.model';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Form } from 'src/app/shared/form';

@Component({
  selector: 'app-parking-form',
  templateUrl: './parking.component.html',
  styleUrls: ['./parking.component.css']
})
export class ParkingFormComponent extends Form implements OnInit {

  @Input() parkings: Parking[] = [];

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

  constructor(
    private parkingService: ParkingService
  ) { super(); }

  ngOnInit() {
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
  register() {

    if (!this.validate()) {
      return;
    }

    let payload: Parking = new Parking(
      this.parkingForm.controls.description.value,
      this.parkingForm.controls.slots.value,
      this.parkingForm.controls.price.value,
    );
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
  }

}
