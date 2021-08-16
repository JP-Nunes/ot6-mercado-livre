package com.ot6.mercadolivre.sale.dtos;

import com.ot6.mercadolivre.sale.Sale;
import com.ot6.mercadolivre.sale.Transaction;

import javax.validation.constraints.NotNull;

public class PaymentByPagseguroRequest implements PaymentRequest {

    @NotNull
    private Long transactionId;

    @NotNull
    private PagseguroTransactionStatusRequest status;

    public PaymentByPagseguroRequest(Long transactionId, PagseguroTransactionStatusRequest status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentReturnRequest{" +
                "transactionId=" + transactionId +
                ", status=" + status +
                '}';
    }

    @Override
    public Transaction toTransaction(Sale sale) {
        return new Transaction(status.toTransactionStatus(), this.transactionId, sale);
    }
}
