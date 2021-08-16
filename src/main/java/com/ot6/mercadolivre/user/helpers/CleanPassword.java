package com.ot6.mercadolivre.user.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Representa uma senha que não passou por nenhuma criptografia
 * Possui um método para criptografar uma senha
 */

public class CleanPassword {

    private String password;

    public CleanPassword(String password) {
        this.password = password;
    }

    public String encode() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
