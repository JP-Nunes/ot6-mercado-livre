package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.dtos.ProductFeatureResponse;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "product_features")
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ManyToOne
    private Product product;

    @Deprecated
    public ProductFeature() {}

    public ProductFeature(String name, String description, Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public ProductFeatureResponse toProductFeatureResponse() {
        return new ProductFeatureResponse(this.name, this.description);
    }
}
