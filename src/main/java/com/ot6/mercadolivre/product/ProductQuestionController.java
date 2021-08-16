package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.dtos.NewQuestionRequest;
import com.ot6.mercadolivre.shared.email.Emails;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ProductQuestionController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductQuestionRepository questionRepository;

    @Autowired
    Emails emails;

    @Transactional
    @PostMapping("/products/{productId}/questions")
    public ResponseEntity<?> create(
            @RequestBody @Valid NewQuestionRequest questionRequest,
            @PathVariable Long productId
    ) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não cadastrado");
        }

        ProductQuestion question = questionRequest.toEntity(product.get(), userRepository);
        questionRepository.save(question);

        emails.newQuestion(question);

        return ResponseEntity.ok().body(question.toString());
    }

}
