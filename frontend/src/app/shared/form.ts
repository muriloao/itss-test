export class Form {
    message = {
        type: '',
        value: ''
    }
    addSuccessMessage(msg) {
        this.message = {
            type: 'success',
            value: msg
        }
    }
    addErrorMessage(msg: string = 'Não foi possível registrar') {
        this.message = {
            type: 'error',
            value: msg
        }
    }
}