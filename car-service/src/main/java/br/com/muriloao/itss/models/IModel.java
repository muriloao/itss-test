/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import br.com.muriloao.itss.dto.IDto;
import java.io.Serializable;

/**
 *
 * @author Murilo Oliveira
 * @param <T> a generics DTO class
 */
public interface IModel<T extends IDto> extends Serializable {

    public T toDto();
}
