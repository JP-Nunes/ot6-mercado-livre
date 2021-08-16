package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.product.ProductImage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductWithImageResponse {

    private String name;
    private Set<ProductImageResponse> images = new HashSet<>();

    public ProductWithImageResponse(String name, Set<ProductImage> images) {
        this.name = name;

        Set<ProductImageResponse> imagesResponse = images.stream()
                .map(image -> image.toProductImageResponse())
                .collect(Collectors.toSet());

        this.images.addAll(imagesResponse);
    }

    public String getName() {
        return name;
    }

    public Set<ProductImageResponse> getImages() {
        return images;
    }
}
