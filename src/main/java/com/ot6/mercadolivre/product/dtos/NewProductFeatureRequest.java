package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.product.ProductFeature;

import javax.validation.constraints.NotBlank;

public class NewProductFeatureRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public NewProductFeatureRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "NewProductFeaturesRequest{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        try {
            NewProductFeatureRequest feature = (NewProductFeatureRequest) obj;
            return this.name == feature.getName();
        } catch (Exception e) {
            return false;
        }
    }

    public ProductFeature toEntity(Product product) {
        return new ProductFeature(this.name, this.description, product);
    }
}
