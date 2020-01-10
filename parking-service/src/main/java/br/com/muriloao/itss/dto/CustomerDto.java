/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.dto;

import br.com.muriloao.itss.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author muriloao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto implements IDto<Customer> {

    private Long id;

    @Override
    public Customer toModel() {
        return new Customer(this.id);
    }

}
