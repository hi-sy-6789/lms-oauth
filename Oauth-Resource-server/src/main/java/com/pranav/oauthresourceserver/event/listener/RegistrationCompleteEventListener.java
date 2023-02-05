package com.pranav.oauthresourceserver.event.listener;

import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.event.RegistrationCompleteEvent;
import com.pranav.oauthresourceserver.service.EmailSenderService;
import com.pranav.oauthresourceserver.service.VerificationTokenService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationToken(user, token);

        String url = event.getApplicationUrl();
        String verificationUrl = url + "/verifyRegistration?token=" + token;

        log.info("Click the link to verify your account: ",    verificationUrl);
        emailSenderService.sendMailToUserToVerify(user, verificationUrl);

    }
}
