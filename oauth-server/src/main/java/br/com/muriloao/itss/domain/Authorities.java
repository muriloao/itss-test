/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.domain;

/**
 *
 * @author Murilo Oliveira
 */
public enum Authorities {

    ROLE_USER,
    ROLE_ADMIN;

    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }

        return names;
    }
}
