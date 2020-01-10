/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.exceptions.ResourceNotFoundException;
import br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.models.Customer;
import br.com.muriloao.itss.repositories.CarRepository;
import br.com.muriloao.itss.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author muriloao
 */
@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    public Car save(@Validated Car car) throws UniqueKeyAlreadyRegisteredException, ResourceNotFoundException {
        try {
            // get the customer
            Customer customer = this.customerRepository.findOne(car.getCustomer().getId());
            if (customer == null) {
                // customer not found
                throw new ResourceNotFoundException("customer", "Selecione o done do carro", "customerDto");
            }
            car.setCustomer(customer);
            car.setStatus(Status.OUT);
            return this.repository.save(car);
        } catch (DataIntegrityViolationException e) {
            // cpf in use 409
            throw new UniqueKeyAlreadyRegisteredException("board", "Placa já registrada", "carDto");
        }
    }

    public Car findById(Long id) {
        return this.repository.findOne(id);
    }

    public Page<Car> findAll(Pageable pgbl) {
        return this.repository.findAll(pgbl);
    }

    public boolean delete(Long id) {
        try {
            this.repository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Optional<Car> findByBoard(String board) {
        return this.repository.findByBoard(board);
    }

    public Car update(Long id, Car car) throws UniqueKeyAlreadyRegisteredException {
        Car carDb = this.findById(id);
        if (carDb == null) {
            return null;
        }
        carDb.setBoard(car.getBoard());
        carDb.setColor(car.getColor());
        carDb.setModel(car.getModel());
        try {
            this.repository.save(carDb);
        } catch (DataIntegrityViolationException e) {
            // cpf in use 409
            throw new UniqueKeyAlreadyRegisteredException("board", "Placa já registrado", "carDto");
        }
        return carDb;
    }

    public List<Car> findByStatus(Status status) {
        return this.repository.findByStatus(status);
    }

}
