package com.pranav.oauthresourceserver.controller;

import com.pranav.oauthresourceserver.entity.Message;
import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.entity.VerificationToken;
import com.pranav.oauthresourceserver.error.DuplicateEmailException;
import com.pranav.oauthresourceserver.error.InvalidEmailException;
import com.pranav.oauthresourceserver.event.RegistrationCompleteEvent;
import com.pranav.oauthresourceserver.model.PasswordResetReqAfterVerifyModel;
import com.pranav.oauthresourceserver.model.PasswordResetReqBefVerifyModel;
import com.pranav.oauthresourceserver.model.UserModel;
import com.pranav.oauthresourceserver.service.*;
import com.pranav.oauthresourceserver.util.SpringSecurityClientUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private AssignRoleToUserTokenService assignRoleToUserTokenService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserModel userModel,
                                             final HttpServletRequest httpServletRequest)
            throws DuplicateEmailException {

        User user= userService.registerUser(userModel);
        if(user!=null){
            eventPublisher.publishEvent(new RegistrationCompleteEvent(user,
                    SpringSecurityClientUtil.getApplicationUrl(httpServletRequest)));
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/register/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token, final HttpServletRequest httpServletRequest) {
        String result = verificationTokenService.verifyToken(token, httpServletRequest);
        if(result.equalsIgnoreCase("valid")) {
            return "User Verified Successfully";
        }
        return result;
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        VerificationToken verificationToken
                = verificationTokenService.generateNewVerificationTokenFromOldToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, SpringSecurityClientUtil.getApplicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Message> resetPassword(@RequestBody @Valid PasswordResetReqBefVerifyModel
                                                             passwordResetReqBefVerifyModel, final HttpServletRequest request)
            throws InvalidEmailException {
        User user = userService.findUserByEmail(passwordResetReqBefVerifyModel.getEmail());
        if(!userService.checkForValidPassword(user, passwordResetReqBefVerifyModel.getOldPassword())) {
            return new ResponseEntity<>(new Message(HttpStatus.BAD_REQUEST,
                    "Invalid Old Password, Kindly enter valid password"), HttpStatus.BAD_REQUEST);
        }
        String url="";
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user,token);
        log.info(user.getEmail());
        passwordResetTokenMail(user,SpringSecurityClientUtil.getApplicationUrl(request), token);
        return new ResponseEntity<>(new Message(HttpStatus.ACCEPTED,
                "Reset Password mail has been sent"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/resetPassword/saveNewPassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordResetReqAfterVerifyModel passwordResetReqAfterVerifyModel) {
        String result = passwordResetTokenService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "Invalid Token";
        }
        Optional<User> user = passwordResetTokenService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordResetReqAfterVerifyModel.getNewPassword());
            return "Password Reset Successfully";
        } else {
            return "Invalid Token";
        }
    }

    @GetMapping("/assignAdminUser")
    public String assignAdminUserRole(@RequestParam("token") String token) {
        String result = assignRoleToUserTokenService.verifyToken(token);
        if(result.equalsIgnoreCase("RoleAssigned")) {
            return "User has been assigned Admin role Successfully";
        }
        return result;
    }


    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        emailSenderService.sendMailToUserToVerify(user, url);
        log.info("Click the link to verify your account: {}",
                url);
    }

    private void passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl  + "/saveNewPassword?token=" + token;
        emailSenderService.sendMailToUserToResetPassword(user, url);
        log.info("Click the link to Reset your Password: {}",
                url);
    }



}
