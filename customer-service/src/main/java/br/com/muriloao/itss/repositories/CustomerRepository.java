/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.models.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author muriloao
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findByCpf(String cpf);

    public Boolean existsByCpf(String cpf);

}
