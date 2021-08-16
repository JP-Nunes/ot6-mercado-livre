package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.user.dtos.UserDetailsResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDetailsResponse {

    private Long id;
    private String name;
    private BigDecimal valor;
    private Integer quantity;
    private Set<ProductFeatureResponse> features = new HashSet<>();
    private String description;
    private ProductCategoryResponse category;
    private UserDetailsResponse seller;
    private Set<ProductImageResponse> images = new HashSet<>();
    private List<ProductOpinionResponse> opinions = new ArrayList<>();
    private Long averageGrade = 0L;
    private String creationDate;

    public ProductDetailsResponse(
            Long id,
            String name,
            BigDecimal valor,
            Integer quantity,
            Set<ProductFeatureResponse> features,
            String description,
            ProductCategoryResponse category,
            UserDetailsResponse seller,
            Set<ProductImageResponse> images,
            List<ProductOpinionResponse> opinions,
            String creationDate
    ) {
        this.id = id;
        this.name = name;
        this.valor = valor;
        this.quantity = quantity;
        this.features.addAll(features);
        this.description = description;
        this.category = category;
        this.seller = seller;
        this.images.addAll(images);
        this.opinions.addAll(opinions);
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Set<ProductFeatureResponse> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategoryResponse getCategory() {
        return category;
    }

    public UserDetailsResponse getSeller() {
        return seller;
    }

    public Set<ProductImageResponse> getImages() {
        return images;
    }

    public List<ProductOpinionResponse> getOpinions() {
        return opinions;
    }

    public Long getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Long averageGrade) {
        this.averageGrade = averageGrade;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
