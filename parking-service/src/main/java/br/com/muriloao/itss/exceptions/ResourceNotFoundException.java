/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.exceptions;

import br.com.muriloao.itss.errors.ErrorResponse;
import br.com.muriloao.itss.errors.ObjectError;
import java.util.Arrays;
import lombok.Getter;

/**
 *
 * @author muriloao
 */
@Getter
public class ResourceNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1869300553614629710L;

    private final ErrorResponse response;

    public ResourceNotFoundException(String field, String message, String objectName) {
        super(message);
        this.response = new ErrorResponse("Registro não encontrado", 404, "Not Found", objectName, Arrays.asList(new ObjectError(message, field)));
    }

    public ResourceNotFoundException(ErrorResponse response, Throwable trhows) {
        super(response.getMessage(), trhows);
        this.response = response;
    }
}
