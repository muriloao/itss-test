import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import {  Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  items: MenuItem[];

  constructor(private route: Router) { }
  onLogout(){
    localStorage.setItem('token', '');
    this.route.navigate(['/']);

  }
  ngOnInit() {
        this.items = [
      {
        label: 'Home',
        routerLink: '/dashboard'
      },
      {
        label: 'Clientes',
        routerLink: '/customer'
      },
      {
        label: 'Veículos',
        routerLink: '/car'
      },
      {
        label: 'Estacionamentos',
        routerLink: '/parking'
      }
    ];
  }

}
