import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CustomerComponent } from './customer-page/customer.component';
import { ParkingComponent } from './parking-page/parking.component';
import { CarComponent } from './car-page/car.component';
import { LoginComponent } from './login-page/login.component';


const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'customer', component: CustomerComponent },
  { path: 'parking', component: ParkingComponent },
  { path: 'car', component: CarComponent },
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
