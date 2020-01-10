/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Murilo Oliveira
 */
@RestController
@RequestMapping("/")
public class AuthController {

    @RequestMapping("/user")
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }
}
