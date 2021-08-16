package com.ot6.mercadolivre.sale.dtos;

import com.ot6.mercadolivre.sale.PaymentGateway;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseRequest {

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    private Long productId;

    @NotNull
    private PaymentGateway gateway;

    public PurchaseRequest(Integer quantity, Long productId, PaymentGateway gateway) {
        this.quantity = quantity;
        this.productId = productId;
        this.gateway = gateway;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }
}
