package com.ot6.mercadolivre.sale.dtos;

import com.ot6.mercadolivre.sale.TransactionStatus;

public enum PagseguroTransactionStatusRequest {

    SUCESSO, ERRO;


    public TransactionStatus toTransactionStatus() {
        if(this.equals(SUCESSO)) {
            return TransactionStatus.SUCESSO;
        }

        return TransactionStatus.ERRO;
    }
}
