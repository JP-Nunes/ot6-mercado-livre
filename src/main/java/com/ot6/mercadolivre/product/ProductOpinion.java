package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.product.dtos.NewOpinionResponse;
import com.ot6.mercadolivre.product.dtos.ProductOpinionResponse;
import com.ot6.mercadolivre.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "product_opinions")
public class ProductOpinion {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer grade;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @Deprecated
    public ProductOpinion() {}

    public ProductOpinion(
            Integer grade,
            String title,
            String description,
            Product product,
            User user)
    {
        this.grade = grade;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public NewOpinionResponse toNewOpinionResponse() {
        return new NewOpinionResponse(
                this.grade,
                this.title,
                this.description,
                this.product.toNewProductResponse(),
                this.user.toNewUserResponse()
        );
    }

    public ProductOpinionResponse toProductOpinionResponse() {
        return new ProductOpinionResponse(
                this.title,
                this.description,
                this.user.toUserDetailsResponse()
        );
    }
}
