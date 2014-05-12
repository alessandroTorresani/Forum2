/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import db.User;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Alessandro
 */
public class Mailer {

    static Logger log = Logger.getLogger(Mailer.class.getName());

    public boolean sendEmail(String email, String title, String text) throws MessagingException {

        final String username = "webforum2014@gmail.com";
        final String password = "forum2014";

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        boolean sent = false;
        try {
            // Nuovo messaggio
            Message message = new MimeMessage(session);
            // Mittente
            message.setFrom(new InternetAddress("webforum2014"));
            // Destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            // Inserisce l'oggetto del messaggio
            message.setSubject(title);
            // Setta il tipo del messaggio e il testo
            message.setContent(text, "text/html");
            // Invia il messaggio
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e);
            log.debug("Messaggio non inviato!");
            sent = false;
        }
        log.debug("Messaggio inviato!");
        sent = true;

        return sent;
    }
}
