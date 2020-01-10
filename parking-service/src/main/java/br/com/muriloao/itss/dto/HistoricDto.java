/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.dto;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.models.Historic;
import br.com.muriloao.itss.models.Parking;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Murilo Oliveira
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoricDto implements IDto<Historic> {

    Long id;

    @NotNull(message = "{car.not.null}")
    @ManyToOne
    Car car;

    @NotNull(message = "{parking.not.null}")
    @ManyToOne
    Parking parking;

    Status status;

    Long checkin;

    Long checkout;

    Double totalPrice;

    String time;

    @Override
    public Historic toModel() {
        return new Historic(this.id, this.car, this.parking, this.status, this.checkin, this.checkout, this.totalPrice, this.time);
    }

}
