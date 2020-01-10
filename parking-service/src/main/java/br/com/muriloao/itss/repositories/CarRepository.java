/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.models.Car;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author muriloao
 */
@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

    @Modifying
    @Query("UPDATE Car c SET c.status=?1 WHERE c.id=?2")
    public int updateSetStatus(Status status, Long id);

}
