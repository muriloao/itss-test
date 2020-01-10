/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author muriloao
 */
@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    public Car findById(Long id) {
        return this.repository.findOne(id);
    }

    int updateCarStatus(Car car, Status status) {
        return this.repository.updateSetStatus(status, car.getId());
    }

}
