package com.ot6.mercadolivre.product.dtos;

public class ProductFeatureResponse {

    private String name;
    private String description;

    public ProductFeatureResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
