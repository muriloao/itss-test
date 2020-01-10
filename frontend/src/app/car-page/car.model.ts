import { Customer } from '../customer-page/customer.model';

export class Car {

    id: number;
    board: string;
    model: string;
    color: string;
    customer: Customer;
    constructor(id: number = null) {
        this.id = id;
    }
}