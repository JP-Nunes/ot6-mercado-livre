package com.ot6.mercadolivre.user.dtos;

public class NewUserResponse {

    private Long id;
    private String email;

    public NewUserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
