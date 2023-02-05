package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.entity.VerificationToken;
import com.pranav.oauthresourceserver.event.AssignRoleToUserEvent;
import com.pranav.oauthresourceserver.repository.UserRepository;
import com.pranav.oauthresourceserver.repository.VerificationTokenRepository;
import com.pranav.oauthresourceserver.util.SpringSecurityClientUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken= new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String verifyToken(String token, HttpServletRequest httpServletRequest) {

        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);

        if (!optionalVerificationToken.isPresent()) {
            return "Invalid Token or User";
        }
        VerificationToken verificationToken= optionalVerificationToken.get();

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        String url= generateUrl(httpServletRequest, token);
        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {

            return "Mail has expired.If you wish to receive another Verification mail again kindly click on the link : "
                    + url;
        }
        verificationTokenRepository.delete(verificationToken);
        user.setEnabled(true);
        userRepository.save(user);
        eventPublisher.publishEvent(new AssignRoleToUserEvent(user, SpringSecurityClientUtil.getApplicationUrlWithoutRequestURI(httpServletRequest)));
        return "valid";

    }

    private String generateUrl(HttpServletRequest httpServletRequest, String token) {
        return SpringSecurityClientUtil.getApplicationUrlWithoutRequestURI(httpServletRequest)
                +"/resendVerifyToken?token="+token;
    }

    @Override
    public VerificationToken generateNewVerificationTokenFromOldToken(String oldToken) {
        Optional<VerificationToken> optionalVerificationToken =verificationTokenRepository.findByToken(oldToken);
        VerificationToken verificationToken= optionalVerificationToken.get();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}
