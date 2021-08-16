package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_questions")
public class ProductQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private LocalDateTime creationDate = LocalDateTime.now();

    public ProductQuestion(String title, Product product, User user) {
        this.title = title;
        this.product = product;
        this.user = user;
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getProductOwnerEmail() {
        return product.getUser().getEmail();
    }

    @Override
    public String toString() {
        return "ProductQuestion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", creationDate=" + creationDate +
                '}';
    }
}
