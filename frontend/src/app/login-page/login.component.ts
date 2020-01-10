import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { LoginService } from './login.service';
import { Form } from '../shared/form';
import { Router } from '@angular/router';
import { GenericService } from '../shared/service/generic-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends Form implements OnInit {
  loginForm: FormGroup = new FormGroup({
    email: new FormControl(),
    password: new FormControl(),
  });
  errors = {
    email: '',
    password: ''
  }
  message = {
    type: '',
    value: ''
  }
  constructor(
    private loginService: LoginService,
    private router: Router
  ) { super() }

  ngOnInit() {
  }


  validate() {
    this.reset();
    let pass = true;
    if (!this.loginForm.controls.email.value) {
      this.errors.email = 'Informe o email';
      pass = false;
    }
    if ((!this.loginForm.controls.password.value)) {
      this.errors.password = 'Informe a senha';
      pass = false;
    }
    return pass;
  }

  reset() {
    this.errors = {
      email: '',
      password: '',
    };
  }

  register() {
    if (!this.validate()) {
      return;
    }
    this.reset();

    this.loginService.login(this.loginForm.controls.email.value, this.loginForm.controls.password.value).subscribe((data) => {
      // sucess
      console.log('Response');
      console.log(data);
      localStorage.setItem('token', data.token_type + ' ' + data.access_token);
      this.loginForm.reset();
      // go to dash
      this.router.navigate(['/dashboard']);
    }, error => {
      console.log(error);
      // error
      if (error.status == 400 || error.status == 409) {
        // bad request
        this.message.type = 'error';
        this.message.value = 'Usuário e/ou senha inválidos';
        return;
      } else {
        // error generico
        super.addErrorMessage();
      }
    });
  }

}
