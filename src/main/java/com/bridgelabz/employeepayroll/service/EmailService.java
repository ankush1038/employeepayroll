package com.bridgelabz.employeepayroll.service;/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){

    }*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    /**
     * Sends a simple email message to the specified recipient.
     *
     * @param toEmail Recipient's email address.
     * @param subject Subject line of the email.
     * @param body    Body content of the email.
     */

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("ankusharma1029@gmail.com");
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);
        System.out.println("Email sent to the user !!");
    }

}