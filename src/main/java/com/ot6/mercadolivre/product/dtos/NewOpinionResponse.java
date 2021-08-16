package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.user.dtos.NewUserResponse;

public class NewOpinionResponse {

    private Integer grade;
    private String title;
    private String description;
    private NewProductResponse product;
    private NewUserResponse user;

    public NewOpinionResponse(
            Integer grade,
            String title,
            String description,
            NewProductResponse product,
            NewUserResponse user
    ) {
        this.grade = grade;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public NewProductResponse getProduct() {
        return product;
    }

    public NewUserResponse getUser() {
        return user;
    }
}
