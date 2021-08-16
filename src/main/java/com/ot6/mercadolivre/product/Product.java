package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.category.Category;
import com.ot6.mercadolivre.product.dtos.*;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<ProductFeature> features = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImage> images = new HashSet<>();

    private LocalDateTime creationDate = LocalDateTime.now();

    @Deprecated
    public Product() {}

    public Product(
            String name,
            BigDecimal valor,
            Integer quantidade,
            List<NewProductFeatureRequest> featuresRequest,
            String description,
            Category category,
            User user
    ) {
        this.name = name;
        this.valor = valor;
        this.quantity = quantidade;
        this.description = description;
        this.category = category;
        this.user = user;

        Set<ProductFeature> newFeatures =
                featuresRequest.stream()
                        .map(featureRequest -> featureRequest.toEntity(this))
                        .collect(Collectors.toSet());

        this.features.addAll(newFeatures);
    }

    public User getUser() {
        return this.user;
    }

    public NewProductResponse toNewProductResponse() {
        return new NewProductResponse(this.name);
    }

    public ProductDetailsResponse toProductDetailsResponse(ProductOpinionRepository opinionRepository) {
        List<ProductOpinion> opinions = opinionRepository.findAllByProductId(this.id);
        Optional<Long> averageGrade = opinionRepository.getProductAverageEvaluation(this.id);

        ProductDetailsResponse detailsResponse = new ProductDetailsResponse(
                this.id,
                this.name,
                this.valor,
                this.quantity,
                this.features.stream().map(ProductFeature::toProductFeatureResponse).collect(Collectors.toSet()),
                this.description,
                this.category.toProductCategoryResponse(),
                this.user.toUserDetailsResponse(),
                this.images.stream().map(ProductImage::toProductImageResponse).collect(Collectors.toSet()),
                opinions.stream().map(ProductOpinion::toProductOpinionResponse).collect(Collectors.toList()),
                this.creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

        if (averageGrade.isPresent()) {
            detailsResponse.setAverageGrade(averageGrade.get());
        }

        return detailsResponse;
    }

    public void addImages(Set<String> links) {
        Set<ProductImage> images = links.stream()
                .map(link -> new ProductImage(link, this))
                .collect(Collectors.toSet());

        this.images.addAll(images);
    }

    public ProductWithImageResponse toNewProductWithImageResponse() {
        return new ProductWithImageResponse(this.name, this.images);
    }

    public boolean belongsToLoggedUser(UserRepository userRepository) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authUser = (UserDetails) auth.getPrincipal();
        String email = authUser.getUsername();

        User loggedUser = userRepository.findByEmail(email).get();
        return this.user == loggedUser;
    }

    public boolean destock(Integer quantity) {
        Assert.isTrue(quantity > 0, "A quantidade de itens deve ser maior que 0");

        if (this.quantity >= quantity) {
            this.quantity -= quantity;
            return true;
        }

        return false;
    }
}
