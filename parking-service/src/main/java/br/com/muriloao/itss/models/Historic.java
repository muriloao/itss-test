/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import br.com.muriloao.itss.dto.HistoricDto;
import br.com.muriloao.itss.enuns.Status;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "historic")
public class Historic implements IModel<HistoricDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull(message = "{car.not.null}")
    @ManyToOne
    Car car;

    @NotNull(message = "{parking.not.null}")
    @ManyToOne
    Parking parking;

    @NotNull(message = "{action.not.null}")
    @Enumerated(EnumType.STRING)
    Status status;

    @NotNull(message = "{timestamp.not.null}")
    Long checkin;

    Long checkout;

    Double totalPrice;

    @Transient
    String time;

    @Override
    public HistoricDto toDto() {
        return new HistoricDto(this.id, this.car, this.parking, this.status, this.checkin, this.checkout, this.totalPrice, this.time);
    }
}
