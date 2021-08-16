package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.dtos.ProductImageResponse;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @URL
    private String link;

    @NotNull
    @ManyToOne
    private Product product;

    @Deprecated
    public ProductImage() {}

    public ProductImage(String link, Product product) {
        this.link = link;
        this.product = product;
    }

    public ProductImageResponse toProductImageResponse() {
        return new ProductImageResponse(this.link);
    }
}
