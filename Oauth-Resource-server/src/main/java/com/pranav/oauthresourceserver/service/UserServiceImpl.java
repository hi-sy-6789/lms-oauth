package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.constants.OAuthResourceServerConstants;
import com.pranav.oauthresourceserver.entity.User;
import com.pranav.oauthresourceserver.error.DuplicateEmailException;
import com.pranav.oauthresourceserver.error.InvalidEmailException;
import com.pranav.oauthresourceserver.model.UserModel;
import com.pranav.oauthresourceserver.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserModel userModel) throws DuplicateEmailException {
        Optional<User> optionalUser= userRepository.findByEmail(userModel.getEmail());
        if(optionalUser.isPresent()){
            throw new DuplicateEmailException("Email already exists. Kindly try to register with a new Email");
        }
        User user= new User();
        user.setEmail(userModel.getEmail());
        user.setRole(OAuthResourceServerConstants.ROLE_USER);
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws InvalidEmailException {
        Optional<User> user= userRepository.findByEmail(email);
        if(!user.isPresent()){
            throw new InvalidEmailException("Email Id is Invalid. Kindly recheck the email and try again");
        }
        return user.get();
    }

    @Override
    public boolean checkForValidPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllActiveMembers() {
        List<User> userList= userRepository.findAllByOrderByFirstNameAscLastNameAsc();
        List<User> activeUserList= new ArrayList<>();
        for(User user: userList){
            if(user.isEnabled())activeUserList.add(user);
        }return activeUserList;
    }

    @Override
    public User get(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public Long getActiveUserTotalCount() {
        return userRepository.findByIsEnabled(true).stream().count();
    }


}
