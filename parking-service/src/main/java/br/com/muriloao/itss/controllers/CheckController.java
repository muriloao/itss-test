/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import br.com.muriloao.itss.dto.HistoricDto;
import br.com.muriloao.itss.exceptions.InvalidStateException;
import br.com.muriloao.itss.exceptions.ResourceNotFoundException;
import br.com.muriloao.itss.models.Historic;
import br.com.muriloao.itss.services.CheckInOutService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CheckController {

    @Autowired
    CheckInOutService checkinService;

    @GetMapping("/checks")
    @ResponseBody
    public ResponseEntity index(Pageable page) {
        return ResponseEntity.ok(this.checkinService.findAll(page));
    }

    @PostMapping("/checkin")
    @ResponseBody
    public ResponseEntity checkin(@RequestBody @Valid HistoricDto historicDto) throws ResourceNotFoundException, InvalidStateException {
        Historic historic = this.checkinService.checkIn(historicDto.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(historic);
    }

    @PutMapping("/checkout/{carid}")
    @ResponseBody
    public ResponseEntity checkout(@PathVariable("carid") Long carid) throws ResourceNotFoundException, InvalidStateException {
        Historic historic = this.checkinService.checkout(carid);
        return ResponseEntity.status(HttpStatus.CREATED).body(historic);
    }
}
