/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.dto;

import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.enuns.Status;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarDto implements IDto<Car> {

    private Long id;

    private Status status = Status.OUT;

    @NotNull(message = "{customer.not.null}")
    private CustomerDto customer;

    @Override
    public Car toModel() {
        return new Car(this.id, this.status, this.customer.toModel());
    }

}
