/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException;
import br.com.muriloao.itss.models.Customer;
import br.com.muriloao.itss.repositories.CustomerRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author muriloao
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(@Valid Customer customer) throws UniqueKeyAlreadyRegisteredException {
        try {
            return this.customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            // cpf in use 409
            throw new UniqueKeyAlreadyRegisteredException("cpf", "CPF já registrado", "customerDto");
        }
    }

    public Customer findById(Long id) {
        return this.customerRepository.findOne(id);
    }

    public Page<Customer> findAll(Pageable pgbl) {
        return this.customerRepository.findAll(pgbl);
    }

    public boolean delete(Long id) {
        try {
            this.customerRepository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Optional<Customer> findByCpf(String cpf) {
        return this.customerRepository.findByCpf(cpf);
    }

    public Customer update(Long id, Customer customer) throws UniqueKeyAlreadyRegisteredException {
        Customer customerDb = this.findById(id);
        if (customerDb == null) {
            return null;
        }
        customerDb.setName(customer.getName());
        customerDb.setPhone(customer.getPhone());
        customerDb.setCpf(customer.getCpf());
        try {
            this.customerRepository.save(customerDb);
        } catch (DataIntegrityViolationException e) {
            // cpf in use 409
            throw new UniqueKeyAlreadyRegisteredException("cpf", "CPF já registrado", "customerDto");
        }
        return customerDb;
    }

}
