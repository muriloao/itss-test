export class Customer {

    id: number;
    name: string;
    cpf: number;
    phone: number;

    constructor(id: number = null) {
        this.id = id;
    }
}