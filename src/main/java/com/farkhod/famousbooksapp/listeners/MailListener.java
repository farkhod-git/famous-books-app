package com.farkhod.famousbooksapp.listeners;

import com.farkhod.famousbooksapp.events.UserEmailEvent;
import com.farkhod.famousbooksapp.service.MailService;
import com.farkhod.famousbooksapp.service.StringValueRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailListener {

    private static final int CODE_EXPIRY_TIME_IN_SECONDS = 120;

    private final MailService mailService;
    private final StringValueRedisService stringValueRedisService;

    @TransactionalEventListener
    @EventListener
    @Async
    public void sendMail(UserEmailEvent userEmailEvent) {
        String email = userEmailEvent.email();
        String code = userEmailEvent.code();
        stringValueRedisService.save("confirmation:code:" + email, code, CODE_EXPIRY_TIME_IN_SECONDS);
        mailService.sendConfirmation(email, code);
        log.info("Mail sent to {}", userEmailEvent);
    }

}
