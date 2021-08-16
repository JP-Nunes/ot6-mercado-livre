package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.sale.dtos.NewInvoiceRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Invoice implements EventsPostSuccessfulPurchase {

    public void process(Sale sale) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity(
                "http://localhost:8080/invoices",
                new NewInvoiceRequest(sale.getId(), sale.getBuyerId()),
                String.class
        );
    }
}
