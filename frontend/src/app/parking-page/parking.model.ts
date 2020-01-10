export class Parking {

    id: number;
    description: string;
    slots: number;
    quantity: number;
    price: number;
    constructor(description: string = '', slots: number = null, price: number = null, id: number = null) {
        this.description = description;
        this.slots = slots;
        this.price = price;
        this.id = id;
    }
}