/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import br.com.muriloao.itss.dto.ParkingDto;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Murilo Oliveira
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
@Entity
@Table(name = "parking")
public class Parking implements IModel<ParkingDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{description.not.blank}")
    private String description;

    @NotNull(message = "{slots.not.null}")
    private Integer slots;

    private Integer quantity = 0;

    @NotNull(message = "{price.not.null}")
    private Double price;

    @OneToMany(mappedBy = "parking", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Historic> historics;

    @Override
    public ParkingDto toDto() {
        return new ParkingDto(this.id, this.description, this.slots, this.quantity, this.price);
    }

}
