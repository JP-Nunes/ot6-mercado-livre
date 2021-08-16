package com.ot6.mercadolivre.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Profile implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
