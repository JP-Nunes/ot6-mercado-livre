package com.ot6.mercadolivre.sale.dtos;

import com.ot6.mercadolivre.sale.Sale;
import com.ot6.mercadolivre.sale.Transaction;

public interface PaymentRequest {

    public Transaction toTransaction(Sale sale);
}
