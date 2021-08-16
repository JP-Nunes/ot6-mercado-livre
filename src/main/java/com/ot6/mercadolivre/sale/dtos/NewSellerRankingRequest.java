package com.ot6.mercadolivre.sale.dtos;

public class NewSellerRankingRequest {

    private Long saleId;
    private Long sellerId;

    public NewSellerRankingRequest(Long saleId, Long sellerId) {
        this.saleId = saleId;
        this.sellerId = sellerId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public Long getSellerId() {
        return sellerId;
    }
}
