/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.models.Parking;
import br.com.muriloao.itss.repositories.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Murilo Oliveira
 */
@Service
public class ParkingService {

    @Autowired
    ParkingRepository parkingRepository;

    public Parking save(@Validated Parking parking) {
        parking.setQuantity(0);
        return this.parkingRepository.save(parking);
    }

    public Parking findById(Long id) {
        return this.parkingRepository.findOne(id);
    }

    public Page<Parking> findAll(Pageable pgbl) {
        return this.parkingRepository.findAll(pgbl);
    }

    public boolean delete(Long id) {
        try {
            this.parkingRepository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Parking update(Long id, Parking parking) {
        Parking parkingDb = this.findById(id);
        if (parkingDb == null) {
            return null;
        }
        parkingDb.setDescription(parking.getDescription());
        parkingDb.setPrice(parking.getPrice());
        parkingDb.setSlots(parking.getSlots());
        return this.save(parkingDb);
    }

    int updateQuantity(int quantity, Long id) {
        return this.parkingRepository.updateQuantity(quantity, id);
    }

}
