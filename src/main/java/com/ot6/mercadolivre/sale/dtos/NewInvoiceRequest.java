package com.ot6.mercadolivre.sale.dtos;

public class NewInvoiceRequest {

    private Long saleId;
    private Long buyerId;

    public NewInvoiceRequest(Long saleId, Long buyerId) {
        this.saleId = saleId;
        this.buyerId = buyerId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public Long getBuyerId() {
        return buyerId;
    }
}
