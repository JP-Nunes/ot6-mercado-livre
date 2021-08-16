package com.ot6.mercadolivre.product.dtos;

public class ProductCategoryResponse {

    private Long id;
    private String name;
    private ProductCategoryResponse motherCategory;

    public ProductCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProductCategoryResponse getMotherCategory() {
        return motherCategory;
    }

    public void setMotherCategory(ProductCategoryResponse motherCategory) {
        this.motherCategory = motherCategory;
    }
}
