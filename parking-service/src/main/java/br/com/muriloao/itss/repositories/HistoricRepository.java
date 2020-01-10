/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.enuns.Status;
import br.com.muriloao.itss.models.Car;
import br.com.muriloao.itss.models.Historic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Murilo Oliveira
 */
@Repository
public interface HistoricRepository extends JpaRepository<Historic, Long> {

    Page<Historic> findByCarAndStatus(Car car, Status status, Pageable page);

}
