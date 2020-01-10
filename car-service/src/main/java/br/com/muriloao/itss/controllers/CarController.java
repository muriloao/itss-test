/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import br.com.muriloao.itss.dto.CarDto;
import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.exceptions.ResourceNotFoundException;
import br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.models.Customer;
import br.com.muriloao.itss.services.CarService;
import br.com.muriloao.itss.services.CustomerService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Murilo Oliveira
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * Get the car with the board number
     *
     * @param status the cpf of the customer to search
     * @return the customer
     */
    @GetMapping("/status/{status}")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Car>> getByStatus(@PathVariable("status") String status) {
        List<Car> cars = this.carService.findByStatus(Status.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(cars);
    }

    /**
     * Get the car with the board number
     *
     * @param board the cpf of the customer to search
     * @return the customer
     */
    @GetMapping("/board/{board}")
    @ResponseBody
    public ResponseEntity<Car> getByBoard(@PathVariable("board") String board) {
        Optional<Car> optCar = this.carService.findByBoard(board);
        if (optCar.isPresent()) {
            return ResponseEntity.ok(optCar.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Persiste de new car in databse
     *
     * @param carDto a data to persist
     * @return the persist's data
     * @throws
     * br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<CarDto> store(@Valid @RequestBody CarDto carDto) throws UniqueKeyAlreadyRegisteredException, ResourceNotFoundException {
        Car car = this.carService.save(carDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(car.toDto());
    }

    /**
     *
     * @param id
     * @param carDto
     * @return
     * @throws UniqueKeyAlreadyRegisteredException
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable(name = "id", required = true) Long id, @RequestBody @Valid CarDto carDto) throws UniqueKeyAlreadyRegisteredException {
        Car car = this.carService.update(id, carDto.toModel());
        if (car != null) {
            return ResponseEntity.ok(car.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get the unique car where id
     *
     * @param id the id of customer
     * @return the customer with id
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<CarDto> show(@PathVariable("id") Long id) {
        Car creditCar = this.carService.findById(id);
        if (creditCar != null) {
            return ResponseEntity.ok(creditCar.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get all cars registered
     *
     * @param page the page to list
     * @return a list of cars
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<Car>> index(Pageable page) {
        return ResponseEntity.ok().body(this.carService.findAll(page));
    }

    /**
     * Delete a car by id
     *
     * @param id id to delete
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (this.carService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
