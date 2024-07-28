/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.contact.scm.entities.User;
import com.contact.scm.helpers.Helper;
import com.contact.scm.services.UserService;

@ControllerAdvice
public class RootController {
    Logger logger = LoggerFactory.getLogger(RootController.class);
    private final UserService userService;

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addLoggedInUser(Model model, Authentication authentication) {
        if(authentication == null)
            return;
            
        String userName = Helper.getEmailOfLogginUser(authentication);
        logger.info("User name : {}", userName);
        User user = userService.getUserByEmail(userName);
        model.addAttribute("loggedInUser", user);
    }

}
