/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import br.com.muriloao.itss.dto.ParkingDto;
import br.com.muriloao.itss.exceptions.UniqueKeyAlreadyRegisteredException;
import br.com.muriloao.itss.models.Parking;
import br.com.muriloao.itss.services.ParkingService;
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
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    /**
     * Persiste de new parking in databse
     *
     * @param parkingDto a data to persist
     * @return the persist's data
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<ParkingDto> store(@Valid @RequestBody ParkingDto parkingDto) {
        Parking parking = this.parkingService.save(parkingDto.toModel());
        return ResponseEntity.ok(parking.toDto());
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable(name = "id", required = true) Long id, @RequestBody @Valid ParkingDto parkingDto) throws UniqueKeyAlreadyRegisteredException {
        Parking parking = this.parkingService.update(id, parkingDto.toModel());
        if (parking != null) {
            return ResponseEntity.ok(parking.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get the unique parking where id
     *
     * @param id the id of parking
     * @return the parking with id
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ParkingDto> show(@PathVariable("id") Long id) {
        Parking creditParking = this.parkingService.findById(id);
        if (creditParking != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(creditParking.toDto());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get all parkings registered
     *
     * @param page the page to list
     * @return a list of parkings
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<ParkingDto>> index(Pageable page) {
        return ResponseEntity.ok().body(this.parkingService.findAll(page).map(h -> h.toDto()));
    }

    /**
     * Delete a parking by id
     *
     * @param id id to delete
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Long id) {
        this.parkingService.delete(id);
        return ResponseEntity.ok().build();
    }
}
