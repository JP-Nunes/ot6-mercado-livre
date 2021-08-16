package com.ot6.mercadolivre.sale;

import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {

    pagseguro {
        @Override
        String generateReturnUrl(
                Sale sale,
                UriComponentsBuilder uriComponentsBuilder
        ) {
            String urlRetornoPagseguro = uriComponentsBuilder
                    .path("/pagseguro/{id}")
                    .buildAndExpand(sale.getId()).toString();

            return "pagseguro.com/" + sale.getId() + "?redirectUrl="
                    + urlRetornoPagseguro;
        }
    },
    paypal {
        @Override
        String generateReturnUrl(
                Sale sale,
                UriComponentsBuilder uriComponentsBuilder
        ) {
            String urlRetornoPaypal = uriComponentsBuilder
                    .path("/paypal/{id}")
                    .buildAndExpand(sale.getId())
                    .toString();

            return "paypal.com/" + sale.getId() + "?redirectUrl=" + urlRetornoPaypal;
        }
    };

    abstract String generateReturnUrl(
            Sale sale,
            UriComponentsBuilder uriComponentsBuilder
    );
}
