import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { ChartModule } from 'primeng/chart';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { DialogModule } from 'primeng/dialog';
import { ParkingFormComponent } from './shared/components/parking-form/parking.component';
import { InputTextModule } from 'primeng/inputtext';
import { FieldsetModule } from 'primeng/fieldset';
import { ReactiveFormsModule } from '@angular/forms';
import { CarFormComponent } from './shared/components/car-form/car.component';
import { CustomerFormComponent } from './shared/components/customer-form/customer.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { CheckinFormComponent } from './dashboard/components/checkin-form/checkin.component';
import { CheckoutFormComponent } from './dashboard/components/checkout-form/checkout.component';
import { CustomerComponent } from './customer-page/customer.component';
import { TableModule } from 'primeng/table';
import { ParkingComponent } from './parking-page/parking.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { CarComponent } from './car-page/car.component';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { LoginComponent } from './login-page/login.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { httpInterceptorProviders } from './shared/interceptors';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ParkingFormComponent,
    CarFormComponent,
    CustomerFormComponent,
    CheckinFormComponent,
    CheckoutFormComponent,
    CustomerComponent,
    ParkingComponent,
    ParkingFormComponent,
    CarComponent,
    LoginComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ChartModule,
    CardModule,
    ButtonModule,
    PanelModule,
    DialogModule,
    InputTextModule,
    FieldsetModule,
    MultiSelectModule,
    MessageModule,
    MessagesModule,
    TableModule,
    ConfirmDialogModule,
    MenubarModule,
  ],
  providers: [
    ConfirmationService,
    httpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
