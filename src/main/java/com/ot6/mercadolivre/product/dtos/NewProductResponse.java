package com.ot6.mercadolivre.product.dtos;

public class NewProductResponse {

    private String name;

    public NewProductResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
