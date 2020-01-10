/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.models.Car;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author muriloao
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public Optional<Car> findByBoard(String board);

    public List<Car> findByStatus(Status status);

}
