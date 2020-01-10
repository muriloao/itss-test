/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import br.com.muriloao.itss.dto.CustomerDto;
import br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException;
import br.com.muriloao.itss.models.Customer;
import br.com.muriloao.itss.services.CustomerService;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author muriloao
 */
@RestController
@RequestMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Get the customer with the cpf number
     *
     * @param cpf the cpf of the customer to search
     * @return the customer
     */
    @GetMapping("/cpf/{cpf}")
    @ResponseBody
    public ResponseEntity<CustomerDto> getCustomerByCpf(@PathVariable("cpf") String cpf) {
        Optional<Customer> optCustomer = this.customerService.findByCpf(cpf);
        if (optCustomer.isPresent()) {
            return ResponseEntity.ok(optCustomer.get().toDto());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Persiste de new customer in databse
     *
     * @param customerDto a data to persist
     * @return the persist's data
     * @throws
     * br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<CustomerDto> store(@RequestBody @Valid CustomerDto customerDto) throws UniqueKeyAlreadyRegisteredException {
        Customer customer = this.customerService.save(customerDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(customer.toDto());
    }

    /**
     * Update the customer with id
     *
     * @param id the customer id to update
     * @param customerDto customer data to update
     * @return a customer updated
     * @throws UniqueKeyAlreadyRegisteredException
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable(name = "id", required = true) Long id, @RequestBody @Valid CustomerDto customerDto) throws UniqueKeyAlreadyRegisteredException {
        Customer customer = this.customerService.update(id, customerDto.toModel());
        if (customer != null) {
            return ResponseEntity.ok(customer.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get the unique customer where id
     *
     * @param id the id of customer
     * @return the customer with id
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<CustomerDto> show(@PathVariable("id") Long id) {
        Customer creditCustomer = this.customerService.findById(id);
        if (creditCustomer != null) {
            return ResponseEntity.ok(creditCustomer.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get all customers registered
     *
     * @param page the page to list
     * @return a list of customers
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<CustomerDto>> index(Pageable page) {
        return ResponseEntity.ok().body(this.customerService.findAll(page).map(c -> c.toDto()));
    }

    /**
     * Delete a customer by id
     *
     * @param id id to delete
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (this.customerService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
