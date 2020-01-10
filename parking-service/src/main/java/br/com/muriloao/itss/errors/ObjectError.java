/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Murilo Oliveira
 */
@Getter
@AllArgsConstructor
public class ObjectError {

    private final String message;
    private final String field;
}
