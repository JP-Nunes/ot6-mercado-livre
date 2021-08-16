package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.sale.dtos.NewSellerRankingRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SellerRanking implements EventsPostSuccessfulPurchase {

    public void process(Sale sale) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity(
                "http://localhost:8080/seller-rank",
                new NewSellerRankingRequest(sale.getId(), sale.getSellerId()),
                String.class
        );
    }
}
