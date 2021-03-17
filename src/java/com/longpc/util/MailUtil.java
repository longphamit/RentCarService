/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ASUS
 */
public class MailUtil {

    public static final String HOST_NAME = "smtp.gmail.com";

    public static final int SSL_PORT = 465; // Port for SSL

    public static final int TSL_PORT = 587; // Port for TLS/STARTTLS

    public static final String APP_EMAIL = "phamlong992k@gmail.com"; // your email

    public static final String APP_PASSWORD = "*******"; // your password
    public static int MIN=100;
    public static int MAX=999;
     public static String TITLE="LongPC Rencar verify Code. Please enter that code to verify your email";
    public static String CONTENT="Your Code: ";
    public static String FOOTER_CONTENT="Thank you to use my services !";
    public static int sendEmail(String receiveMail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", SSL_PORT);
        int num=getRandomNumber();
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveMail));
            StringBuilder content= new StringBuilder();
            
            content.append(CONTENT+"\n");
            content.append(num+"\n");
            content.append(FOOTER_CONTENT+"\n");
            message.setSubject(TITLE);
            message.setText(content.toString());
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return num;
    }
    public static int getRandomNumber() {
        return (int) ((Math.random() * (MAX - MIN)) + MIN);
    }
}
