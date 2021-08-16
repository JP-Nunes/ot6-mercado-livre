package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.user.dtos.UserDetailsResponse;

public class ProductOpinionResponse {

    private String title;
    private String description;
    private UserDetailsResponse user;

    public ProductOpinionResponse(String title, String description, UserDetailsResponse user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserDetailsResponse getUser() {
        return user;
    }
}
