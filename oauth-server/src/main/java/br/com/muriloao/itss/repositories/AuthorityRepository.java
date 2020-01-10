/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.repositories;

import br.com.muriloao.itss.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Murilo Oliveira
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findByName(String name);

}
