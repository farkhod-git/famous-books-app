package com.farkhod.famousbooksapp.service.impl;

import com.farkhod.famousbooksapp.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailMailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendConfirmation(String to, String confirmation) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("farkhod.go@gmail.com");
        smm.setTo(to);
        smm.setSubject("Confirmation");
        smm.setText(confirmation);
        mailSender.send(smm);
    }
}
