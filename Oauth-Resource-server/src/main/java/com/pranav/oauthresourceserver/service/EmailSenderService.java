package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String adminMail;


    public void sendMailToUserToVerify(User user, String verificationUrl) {
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(adminMail);
        mailMessage.setSubject("Verification Mail");
        mailMessage.setText("kindly click on the given link to verify your account " +
                "on the SpringBootEmailClient Application "+verificationUrl);
        javaMailSender.send(mailMessage);
        log.info("Verification Mail has been sent to user: ", user.getEmail());
    }

    public void sendMailToUserToResetPassword(User user, String url) {
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(adminMail);
        mailMessage.setSubject("Password Reset Mail");
        mailMessage.setText("kindly click on the given link to Reset the password " +
                "on the SpringBootEmailClient Application "+url);
        javaMailSender.send(mailMessage);
        log.info("Password Reset Mail has been sent to user:",user.getEmail());
    }

    public void sendMailToAdminToAssignAdminRoleToUser(String assignAdminUrl, User user) {
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setTo(adminMail);
        mailMessage.setFrom(adminMail);
        mailMessage.setSubject("New User Login. Assign Admin user Mail");
        String lastName="";
        lastName= user.getLastName();
        String firstName=user.getLastName();
        String email= user.getEmail();
        mailMessage.setText("A new user has signed with name :: "+firstName+" "+lastName+" "+
                        "and email address :: "+ email +
                "kindly click on the given link to assign Admin role to the user. " +
                "on the SpringBootEmailClient Application "+assignAdminUrl);
        javaMailSender.send(mailMessage);
        log.info("Mail has been sent to the user to assign Admin role to the user. ");
    }
}
