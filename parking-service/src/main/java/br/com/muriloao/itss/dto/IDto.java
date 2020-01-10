/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.dto;

import br.com.muriloao.itss.models.IModel;
import java.io.Serializable;

/**
 *
 * @author Murilo Oliveira
 * @param <T> a generics object do serialize
 */
public interface IDto<T extends IModel> extends Serializable {

    public T toModel();
}
