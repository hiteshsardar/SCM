/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.contact.scm.entities.ProviderConverter;
import com.contact.scm.entities.User;
import com.contact.scm.helpers.AppConstants;
import com.contact.scm.helpers.Helper;
import com.contact.scm.helpers.ResourceNotFoundException;
import com.contact.scm.repositories.UserRepo;
import com.contact.scm.services.EmailService;
import com.contact.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final String subject = "SCM : Vefiry Email Account";
    
    
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User saveUser(User user) {
        // generate random user ID
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRolList(List.of(AppConstants.ROLE_USER, AppConstants.ROLE_ADMIN));
        user.setProvider(new ProviderConverter().convert("self"));
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User saveUser = userRepo.save(user);

        String emailLink = Helper.getLinkForEmailVerification(emailToken);
        emailService.sendEmail(saveUser.getEmail(), subject, emailLink);

        return saveUser;
    }

    @Override
    public Optional<User> getUserById(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User getUser = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        getUser.setName(user.getName());
        getUser.setEmail(user.getEmail());
        getUser.setPassword(user.getPassword());
        getUser.setAbout(user.getAbout());
        getUser.setPhoneNumber(user.getPhoneNumber());
        getUser.setEnabled(user.isEnabled());
        getUser.setEmailVerified(user.isEmailVerified());
        getUser.setPhoneVerified(user.isPhoneVerified());
        getUser.setProvider(user.getProvider());
        getUser.setProviderUserId(user.getProviderUserId());

        // save the user details
        User save = userRepo.save(getUser);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String userId) {
        User getUser = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        userRepo.delete(getUser);
    }

    @Override
    public boolean isUserExist(String userId) {
        User getUser = userRepo.findById(userId).orElse(null);
        return getUser != null ? true : false; 
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User getUser = userRepo.findByEmail(email).orElse(null);
        return getUser != null ? true : false; 
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
