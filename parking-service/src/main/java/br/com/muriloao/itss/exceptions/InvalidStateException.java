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
public class InvalidStateException extends Exception {

    private final ErrorResponse response;

    public InvalidStateException(String field, String message, String objectName) {
        super(message);
        this.response = new ErrorResponse("Não é possível realizar o checkin/checkout", 400, "Invalid Statu", objectName, Arrays.asList(new ObjectError(message, field)));
    }

    public InvalidStateException(ErrorResponse response, Throwable trhows) {
        super(response.getMessage(), trhows);
        this.response = response;
    }
}
