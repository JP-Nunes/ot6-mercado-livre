package com.ot6.mercadolivre.product.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.product.ProductQuestion;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NewQuestionRequest(String title) {
        this.title = title;
    }

    public ProductQuestion toEntity(Product product, UserRepository userRepository) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authUser = (UserDetails) auth.getPrincipal();
        String email = authUser.getUsername();

        User user = userRepository.findByEmail(email).get();

        return new ProductQuestion(this.title, product, user);
    }
}
