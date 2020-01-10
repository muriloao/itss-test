/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.models.Parking;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Murilo Oliveira
 */
@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Parking p SET p.quantity = ?1 WHERE p.id = ?2")
    public int updateQuantity(int quantity, Long id);

}
