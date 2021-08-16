package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.product.ProductRepository;
import com.ot6.mercadolivre.sale.dtos.PurchaseRequest;
import com.ot6.mercadolivre.shared.email.Emails;
import com.ot6.mercadolivre.user.LoggedUser;
import com.ot6.mercadolivre.user.User;
import com.ot6.mercadolivre.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class CloseSaleController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    Emails emails;

    @PostMapping("/sales")
    public ResponseEntity<?> closeSale(
            @RequestBody @Valid PurchaseRequest purchaseRequest,
            UriComponentsBuilder uriBuilder
    ) {
        Product product = productRepository.findById(purchaseRequest.getProductId()).get();

        Integer quantity = purchaseRequest.getQuantity();
        boolean loweredStock = product.destock(quantity);

        if (loweredStock) {
            User buyer = LoggedUser.getLoggedUserFromAuthContext(userRepository);
            PaymentGateway gateway = purchaseRequest.getGateway();
            Sale sale = new Sale(product, quantity, buyer, gateway);
            saleRepository.save(sale);

            emails.newSale(sale);

            return ResponseEntity.ok().body(sale.redirectionUrl(uriBuilder));
        }

         return ResponseEntity
                 .badRequest()
                 .body("Quantidade requerida excedeu o estoque deste produto.");
    }
}
