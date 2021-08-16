package com.ot6.mercadolivre.sale;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;

    @NotNull
    @Positive
    private Long transactionId;

    @ManyToOne
    private Sale sale;

    @NotNull
    private LocalDateTime creationDate = LocalDateTime.now();

    @Deprecated
    public Transaction() {}

    public Transaction(TransactionStatus status, Long transactionId, Sale sale) {
        this.status = status;
        this.transactionId = transactionId;
        this.sale = sale;
    }

    public boolean isSuccessful() {
        return this.status.equals(TransactionStatus.SUCESSO);
    }
}
