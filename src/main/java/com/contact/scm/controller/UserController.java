/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.contact.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // user dashboard page
    @RequestMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    
    //user profile page
    @RequestMapping("/profile")
    public String userProfile(Model model,Authentication authentication) {
        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact

    // user serach contact
}
