/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.dto.CarDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author muriloao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "car")
public class Car implements IModel<CarDto> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "{board.not.blank}")
    private String board;

    @NotBlank(message = "{model.not.blank}")
    private String model;

    @NotBlank(message = "{color.not.blank}")
    private String color;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OUT;

    @NotNull(message = "{customer.not.null}")
    @ManyToOne
    private Customer customer;

    @Override
    public CarDto toDto() {
        return new CarDto(this.id, this.board, this.model, this.color, this.status, this.customer != null ? this.customer.toDto()
                : null);
    }

}
