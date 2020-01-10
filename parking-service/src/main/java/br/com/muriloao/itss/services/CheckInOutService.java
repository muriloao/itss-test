/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.services;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.exceptions.InvalidStateException;
import br.com.muriloao.itss.exceptions.ResourceNotFoundException;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.models.Historic;
import br.com.muriloao.itss.models.Parking;
import br.com.muriloao.itss.repositories.HistoricRepository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Murilo Oliveira
 */
@Service
public class CheckInOutService {

    @Autowired
    HistoricRepository historicRepository;

    @Autowired
    ParkingService parkingService;

    @Autowired
    CarService carService;

    public Page<Historic> findAll(Pageable page) {
        return this.historicRepository.findAll(page);
    }

    /**
     * Persist movimentation with status IN
     *
     * @param historic the data to persist
     * @return a data persisted in managed state
     * @throws ResourceNotFoundException if Cat or Parking not found
     * @throws br.com.muriloao.itss.exceptions.InvalidStateException if the Car
     * already IN
     */
    public Historic checkIn(Historic historic) throws ResourceNotFoundException, InvalidStateException {
        // update to managed objects
        historic = this.menagedHistoric(historic);
        // check the quantity
        if (historic.getParking().getQuantity() + 1 >= historic.getParking().getSlots()) {
            // this parking is full
            throw new InvalidStateException("quantity", "Estacionamento cheio", "parkingDto");
        }
        // verify if the car is in any parking
        if (!Status.OUT.equals(historic.getCar().getStatus())) {
            // car is in inside some parking
            throw new InvalidStateException("car", "Este carro já fez check-in", "carDto");
        }
        // update the status to check IN and CHECKIN TIMESTAMP
        historic.setStatus(Status.IN);
        historic.setCheckin(System.currentTimeMillis());
        // persist the movimentation anr car status
        this.carService.updateCarStatus(historic.getCar(), Status.IN);
        // update de car's quatity
        this.parkingService.updateQuantity(historic.getParking().getQuantity() + 1, historic.getParking().getId());
        return this.historicRepository.save(historic);
    }

    /**
     * Persist de checkout movimentation and calculate price
     *
     * @param carid the car id to persist checkout
     * @return a data persisted with price
     * @throws ResourceNotFoundException if Car or Parking not found
     * @throws InvalidStateException if the Car already OUT
     */
    public Historic checkout(Long carid) throws ResourceNotFoundException, InvalidStateException {
        // limit the query
        Pageable page = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        // search the historic with car in status IN
        List<Historic> historics = this.historicRepository.findByCarAndStatus(new Car(carid, null, null), Status.IN, page).getContent();
        if (historics == null || historics.isEmpty()) {
            // not found
            throw new ResourceNotFoundException("car", "Registro não localizado", "historicDto");
        }
        Historic historic = historics.stream().findFirst().get();

        // verify if the car is IN AND movimentation status
        if ((!Status.IN.equals(historic.getCar().getStatus())) || (!Status.IN.equals(historic.getStatus()))) {
            // car is in inside some parking
            throw new InvalidStateException("car", "Este veículo não está no estacionamento", "carDto");
        }
        // update the status to check OUT and CHECKOUT TIMESTAMP
        this.carService.updateCarStatus(historic.getCar(), Status.OUT);
        this.parkingService.updateQuantity(historic.getParking().getQuantity() - 1, historic.getParking().getId());
        historic.getCar().setStatus(Status.OUT);
        historic.setStatus(Status.OUT);
        historic.setCheckout(System.currentTimeMillis());
        // calculate the price
        historic = this.calculatePriceTotal(historic);
        // persist the movimentation
        return this.historicRepository.save(historic);
    }

    /**
     * Get a managed instance to work
     *
     * @param historic the data to managed
     * @returnthe historic's object in managed state
     * @throws ResourceNotFoundException if Car or Parking not found
     */
    private Historic menagedHistoric(Historic historic) throws ResourceNotFoundException {
        // find the car
        Car car = this.carService.findById(historic.getCar().getId());
        if (car == null) {
            // car not found
            throw new ResourceNotFoundException("car", "Carro não localizado", "carDto");
        }
        // find the parking
        Parking parking = this.parkingService.findById(historic.getParking().getId());
        if (parking == null) {
            // parking not found
            throw new ResourceNotFoundException("parking", "Pátio não localizado", "parkingDto");
        }
        // update to managed objects
        historic.setCar(car);
        historic.setParking(parking);
        return historic;
    }

    /**
     * Calculate the price od parking If time less 15 minutos its free If time
     * between 15 minutes and 1 hour its minimal price
     *
     * @param historic the monimentation to calculate
     * @return the historic with price and time calculated
     */
    private Historic calculatePriceTotal(Historic historic) {
        Double milisseconds = ((Long) (historic.getCheckout() - historic.getCheckin())).doubleValue();
        Double price = historic.getParking().getPrice();
        System.out.println("mili " + milisseconds);
        Double seconds = milisseconds / 1000;
        historic.setTotalPrice(0D);
        if (seconds < 60) {
            // less a minute
            historic.setTime("Menos de um minuto. Tolerância 15min");
            return historic;
        }
        System.out.println("seconds " + seconds);
        Double minutes = seconds / 60;
        if (minutes <= 15) {
            historic.setTime(String.format("%.2f", minutes) + " minutos. Tolerância 15min");
            return historic;
        }
        if (minutes < 60) {
            historic.setTime(String.format("%.2f", minutes) + " minutos. Valor minímo");
            historic.setTotalPrice(price);
            return historic;
        }
        System.out.println("mintus " + minutes);
        Double hours = minutes / 60;
        historic.setTime(hours + " horas");
        historic.setTotalPrice(hours * price);
        System.out.println("hours " + hours);
        return historic;
    }
}
