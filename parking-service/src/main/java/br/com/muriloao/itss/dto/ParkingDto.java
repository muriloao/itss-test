/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.dto;

import br.com.muriloao.itss.models.Parking;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author muriloao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingDto implements IDto<Parking> {

    private Long id;

    @NotBlank(message = "{description.not.blank}")
    private String description;

    @NotNull(message = "{slots.not.null}")
    private Integer slots;

    private Integer quantity;

    @NotNull(message = "{price.not.null}")
    private Double price;

    @Override
    public Parking toModel() {
        return new Parking(this.id, this.description, this.slots, this.quantity, this.price, null);
    }

}
