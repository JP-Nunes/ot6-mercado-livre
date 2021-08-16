package com.ot6.mercadolivre.sale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ot6.mercadolivre.sale.dtos.PaymentByPagseguroRequest;
import com.ot6.mercadolivre.sale.dtos.PaymentByPaypalRequest;
import com.ot6.mercadolivre.sale.dtos.PaymentRequest;
import com.ot6.mercadolivre.shared.email.Emails;
import org.aspectj.asm.HierarchyWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ReturnCloseSaleController {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    EventsPostNewPurchase eventsPostNewPurchase;

    @Transactional
    @PostMapping("/pagseguro/{saleId}")
    public String paymentByPagseguro(
            @RequestBody @Valid PaymentByPagseguroRequest paymentRequest,
            @PathVariable Long saleId
    ) {
        return processPayment(paymentRequest, saleId);
    }

    @Transactional
    @PostMapping("/paypal/{saleId}")
    public String paymentByPaypal(
            @RequestBody @Valid PaymentByPaypalRequest paymentRequest,
            @PathVariable Long saleId
    ) {
        return processPayment(paymentRequest, saleId);
    }

    private String processPayment(PaymentRequest paymentRequest, Long saleId) {
        Sale sale = saleRepository.findById(saleId).get();
        sale.addTransaction(paymentRequest);
        eventsPostNewPurchase.process(sale);

        saleRepository.save(sale);

        return sale.toString();
    }
}
