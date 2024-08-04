/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import com.contact.scm.helpers.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.contact.scm.entities.User;
import com.contact.scm.form.UserForm;
import com.contact.scm.helpers.Message;
import com.contact.scm.helpers.MessageType;
import com.contact.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {
    
    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindResult, HttpSession session) {
        // fetch data
        // validate data
        if(rBindResult.hasErrors()) {
            // add message
            Message message = Message.builder().content("Please resolve the following errors !!!").type(MessageType.red).build();
            session.setAttribute(AppConstants.MESSAGE, message);
            return "register";
        }
        // save the data
        // User
        User user = User.builder()
        .name(userForm.getName())
        .email(userForm.getEmail())
        .password(userForm.getPassword())
        .about(userForm.getAbout())
        .phoneNumber(userForm.getPhoneNumber())
        .enabled(false)
        .build();
        User saveUser = userService.saveUser(user);

        // add message
        Message message = Message.builder().content("Registration successful. Verification link is sent to " + saveUser.getEmail() + ", please verify before login.").type(MessageType.green).build();
        session.setAttribute(AppConstants.MESSAGE, message);
        // redirect to the login page
        return "redirect:/register";
    }
}
