package com.ot6.mercadolivre.product;

import com.ot6.mercadolivre.product.dtos.NewProductImageRequest;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
public class ProductImageController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FakeUploader fakeUploader;

    @Transactional
    @PostMapping("products/{id}/images")
    public ResponseEntity<?> addProductImages(@PathVariable Long id, @Valid NewProductImageRequest imageRequest) {
        Optional<Product> possibleProduct = productRepository.findById(id);

        if (possibleProduct.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não cadastrado");
        }

        Product product = possibleProduct.get();

        if (!product.belongsToLoggedUser(userRepository)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = fakeUploader.send(imageRequest.getImages());
        product.addImages(links);
        productRepository.save(product);
        return ResponseEntity.ok(product.toNewProductWithImageResponse());
    }
}
