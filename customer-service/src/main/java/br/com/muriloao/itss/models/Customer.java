/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import br.com.muriloao.itss.dto.CustomerDto;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "customer")
public class Customer implements IModel<CustomerDto> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @CPF(message = "{cpf.invalid}")
    @Column(unique = true)
    @NotBlank(message = "{cpf.not.blank}")
    private String cpf;

    @NotBlank(message = "{phone.not.blank}")
    String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Car> cars;

    @Override
    public CustomerDto toDto() {
        return new CustomerDto(this.id, this.name, this.cpf, this.phone);
    }

}
