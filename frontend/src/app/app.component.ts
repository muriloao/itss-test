import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Dashboad';
  items: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        routerLink: 'dashboard'
      },
      {
        label: 'Clientes',
        routerLink: 'customer'
      },
      {
        label: 'Veículos',
        routerLink: 'car'
      },
      {
        label: 'Estacionamentos',
        routerLink: 'parking'
      }
    ];
  }
}
