package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.product.ProductOpinion;
import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;

public class NewOpinionRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer grade;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    public NewOpinionRequest(Integer grade, String title, String description) {
        this.grade = grade;
        this.title = title;
        this.description = description;
    }

    public ProductOpinion toEntity(Product product, UserRepository userRepository) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authUser = (UserDetails) auth.getPrincipal();
        String email = authUser.getUsername();

        User user = userRepository.findByEmail(email).get();

        return new ProductOpinion(
                this.grade,
                this.title,
                this.description,
                product,
                user
        );
    }
}
