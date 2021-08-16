package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.sale.dtos.NewInvoiceRequest;
import com.ot6.mercadolivre.sale.dtos.NewSellerRankingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeExternalSystemsController {

    @PostMapping("/invoices")
    public void generateInvoice(@RequestBody NewInvoiceRequest invoiceRequest) throws InterruptedException {
        System.out.println(
                "Id da venda: " + invoiceRequest.getSaleId().toString()
                + ", id do comprador: " + invoiceRequest.getBuyerId().toString()
        );
        Thread.sleep(150);
    }

    @PostMapping("/seller-rank")
    public void sellerRanking(@RequestBody NewSellerRankingRequest rankingRequest) throws InterruptedException {
        System.out.println(
                "Id da venda: " + rankingRequest.getSaleId().toString()
                + ", id do vendedor: " + rankingRequest.getSellerId().toString()
        );
        Thread.sleep(150);
    }
}
