package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.dtos.NewOpinionRequest;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ProductOpinionController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductOpinionRepository opinionRepository;

    @Transactional
    @PostMapping("products/{productId}/opinions")
    public ResponseEntity<?> addProductOpinion(
            @PathVariable Long productId,
            @RequestBody @Valid NewOpinionRequest opinionRequest)
    {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não cadastrado");
        }

        ProductOpinion opinion = opinionRequest.toEntity(product.get(), userRepository);
        opinionRepository.save(opinion);

        return ResponseEntity.ok(opinion.toNewOpinionResponse());
    }
}
