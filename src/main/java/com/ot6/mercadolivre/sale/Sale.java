package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.product.Product;
import com.ot6.mercadolivre.sale.dtos.PaymentByPagseguroRequest;
import com.ot6.mercadolivre.sale.dtos.PaymentRequest;
import com.ot6.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @ManyToOne
    private User buyer;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PaymentGateway gateway;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private SaleStatus status;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Sale() {}

    public Sale(Product product, Integer quantity, User buyer, PaymentGateway gateway) {
        this.product = product;
        this.quantity = quantity;
        this.buyer= buyer;
        this.gateway = gateway;
        this.status = SaleStatus.INICIADA;
    }

    public Long getId() {
        return Id;
    }

    public String getBuyerEmail() {
        return this.buyer.getEmail();
    }

    public Long getBuyerId() {
        return this.buyer.getId();
    }

    public Long getSellerId() {
        return this.product.getUser().getId();
    }

    public String getProductOwnerEmail() {
        return this.product.getUser().getEmail();
    }

    public String redirectionUrl(UriComponentsBuilder uriBuilder) {
        return this.gateway.generateReturnUrl(this, uriBuilder);
    }

    public void addTransaction(PaymentRequest paymentRequest) {
        Transaction transaction = paymentRequest.toTransaction(this);

        Assert.isTrue(!this.transactions.contains(transaction), "Transação já existente");
        Assert.isTrue(getSuccessfulTransactions().isEmpty(), "Essa venda já foi concluída com sucesso");

        this.transactions.add(transaction);
    }

    public boolean wasSuccessfullyProcessed() {
        return !getSuccessfulTransactions().isEmpty();
    }

    private Set<Transaction> getSuccessfulTransactions() {
        Set<Transaction> successfulTransactions =
                this.transactions.stream()
                    .filter(Transaction::isSuccessful)
                    .collect(Collectors.toSet());

        Assert.isTrue(
                successfulTransactions.size() <= 1,
                "Mais de uma transação bem sucedida numa mesma compra"
        );

        return successfulTransactions;
    }
}
