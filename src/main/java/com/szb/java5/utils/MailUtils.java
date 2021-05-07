package com.szb.java5.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 石致彬
 * @since 2021-04-02 8:54
 */
@Service
public class MailUtils {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Async("taskExecutor")
    public void sendSimpleMessage(String title,String context,String ... acceptEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(context);
        message.setFrom(from);
        message.setTo(acceptEmail);
        javaMailSender.send(message);
    }
}
