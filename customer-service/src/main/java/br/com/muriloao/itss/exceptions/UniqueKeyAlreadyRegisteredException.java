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
public class UniqueKeyAlreadyRegisteredException extends Exception {

    private final ErrorResponse response;

    public UniqueKeyAlreadyRegisteredException(String field, String message, String objectName) {
        super(message);
        this.response = new ErrorResponse("Chave única duplicada", 409, "Bad Request", objectName, Arrays.asList(new ObjectError(message, field)));
    }

    public UniqueKeyAlreadyRegisteredException(ErrorResponse response, Throwable trhows) {
        super(response.getMessage(), trhows);
        this.response = response;
    }
}
