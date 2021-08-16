package com.ot6.mercadolivre.shared.email;

public interface Mailer {

    void send(String body, String subject, String nameFrom, String from, String to);
}
