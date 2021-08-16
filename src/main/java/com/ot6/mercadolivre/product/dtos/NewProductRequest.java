package com.ot6.mercadolivre.product.dtos;

import com.ot6.mercadolivre.category.Category;
import com.ot6.mercadolivre.category.CategoryRepository;
import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.shared.validation.ExistingId;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @Size(min = 3)
    @Valid
    private List<NewProductFeatureRequest> features = new ArrayList<>();

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @Positive
    @ExistingId(field = "id", theClass = Category.class, message = "Categoria não registrada")
    private Long categoryId;

    public NewProductRequest(
            String name,
            BigDecimal valor,
            Integer quantity,
            List<NewProductFeatureRequest> features,
            String description,
            Long categoryId)
    {
        this.name = name;
        this.valor = valor;
        this.quantity = quantity;
        this.features.addAll(features);
        this.description = description;
        this.categoryId = categoryId;
    }

    /*
    * Getter necessário para desserialização da lista de features
    */;
    public List<NewProductFeatureRequest> getFeatures() {
        return features;
    }

    public Product toEntity(CategoryRepository categoryRepository, UserRepository userRepository) {
        Category category = categoryRepository.findById(categoryId).get();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedUser = (UserDetails) auth.getPrincipal();
        String email = loggedUser.getUsername();

        User user = userRepository.findByEmail(email).get();

        return new Product(
                this.name,
                this.valor,
                this.quantity,
                this.features,
                this.description,
                category,
                user
        );
    }

    public Set<String> getRepeatedFeaturesNames() {
        List<String> featuresNames = getFeaturesNames();

        return featuresNames
                .stream()
                .filter(name -> Collections.frequency(featuresNames, name) > 1)
                .collect(Collectors.toSet());
    }

    public List<String> getFeaturesNames() {
        return this.features
                .stream()
                .map(NewProductFeatureRequest::getName)
                .collect(Collectors.toList());
    }
}
