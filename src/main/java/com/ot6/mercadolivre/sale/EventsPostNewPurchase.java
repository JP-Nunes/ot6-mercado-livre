package com.ot6.mercadolivre.sale;

import com.ot6.mercadolivre.shared.email.Emails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventsPostNewPurchase {

    @Autowired
    Set<EventsPostSuccessfulPurchase> eventsSuccessful;

    @Autowired
    Emails emails;

    public void process(Sale sale) {
        if (sale.wasSuccessfullyProcessed()) {
            eventsSuccessful.forEach(event -> event.process(sale));
            emails.paymentSucceeded(sale);
        } else {
            emails.paymentFailure(sale);
        }
    }
}
