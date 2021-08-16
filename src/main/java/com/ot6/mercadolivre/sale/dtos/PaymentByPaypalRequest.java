package com.ot6.mercadolivre.sale.dtos;

import com.ot6.mercadolivre.sale.Sale;
import com.ot6.mercadolivre.sale.Transaction;
import com.ot6.mercadolivre.sale.TransactionStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaymentByPaypalRequest implements PaymentRequest {

    @NotNull
    private Long transactionId;

    @NotNull
    @Min(0)
    @Max(1)
    private Integer status;

    public PaymentByPaypalRequest(Long transactionId, Integer status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentByPaypalRequest{" +
                "transactionId=" + transactionId +
                ", status=" + status +
                '}';
    }

    @Override
    public Transaction toTransaction(Sale sale) {
        TransactionStatus transactionStatus =
                this.status == 0 ? TransactionStatus.ERRO : TransactionStatus.SUCESSO;

        return new Transaction(transactionStatus, this.transactionId, sale);
    }
}
