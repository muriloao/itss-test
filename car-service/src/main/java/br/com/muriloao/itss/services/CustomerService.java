/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.models.Customer;
import br.com.muriloao.itss.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author muriloao
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return this.customerRepository.findOne(id);
    }

}
