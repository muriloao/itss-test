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
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

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

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @CPF
    @NotBlank(message = "{cpf.not.blank}")
    private String cpf;

    @NotBlank(message = "{phone.not.blank}")
    private String phone;

    @Override
    public Customer toModel() {
        return new Customer(this.id, this.name, this.cpf, this.phone, null);
    }

}
