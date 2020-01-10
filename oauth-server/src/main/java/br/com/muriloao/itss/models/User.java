/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Murilo Oliveira
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(updatable = false, nullable = false)
    private String username;

    @Size(min = 0, max = 500)
    private String password;

    @Email
    @Size(min = 0, max = 50)
    private String email;

    private boolean activated;

    @Size(min = 0, max = 100)
    @Column(name = "activationkey")
    private String activationKey;

    @Size(min = 0, max = 100)
    @Column(name = "resetpasswordkey")
    private String resetPasswordKey;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;
}
