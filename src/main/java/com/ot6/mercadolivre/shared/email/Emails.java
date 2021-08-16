package com.ot6.mercadolivre.shared.email;

import com.ot6.mercadolivre.product.ProductQuestion;
import com.ot6.mercadolivre.sale.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Emails {

    @Autowired
    Mailer mailer;

    public void newQuestion(ProductQuestion question) {
        mailer.send(
                "<html>....</html>",
                "Question to be answered",
                "question@mercadolivre.com",
                question.getUserEmail(),
                question.getProductOwnerEmail()
        );
    }

    public void newSale(Sale sale) {
        mailer.send(
                "<html>....</html>",
                "New purchase",
                "purchase@mercadolivre.com",
                sale.getBuyerEmail(),
                sale.getProductOwnerEmail()
        );
    }

    public void paymentSucceeded(Sale sale) {
        mailer.send(
                "<html>....</html>",
                "Purchase Success",
                "purchase@mercadolivre.com",
                sale.getProductOwnerEmail(),
                sale.getBuyerEmail()
        );
    }

    public void paymentFailure(Sale sale) {
        mailer.send(
                "<html>....</html>",
                "Purchase Fail",
                "purchase@mercadolivre.com",
                sale.getProductOwnerEmail(),
                sale.getBuyerEmail()
        );
    }
}
