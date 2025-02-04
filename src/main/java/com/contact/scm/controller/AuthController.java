/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import com.contact.scm.helpers.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.contact.scm.entities.User;
import com.contact.scm.helpers.Message;
import com.contact.scm.helpers.MessageType;
import com.contact.scm.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth/")
public class AuthController {
    private final UserRepo userRepo;


    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);
        String redirectToLogin = "redirect:/login";
        if (user != null) {
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);

                session.setAttribute(AppConstants.MESSAGE, Message.builder()
                    .content("Your email is successfully verified.")
                    .type(MessageType.green)
                    .build());
                return redirectToLogin;
            }

            session.setAttribute(AppConstants.MESSAGE, Message.builder()
                .content("Your email is not verified. Please try again.")
                .type(MessageType.red)
                .build());
            return redirectToLogin;
        }

        session.setAttribute(AppConstants.MESSAGE, Message.builder()
            .content("Your email is not verified. Please try again.")
            .type(MessageType.red)
            .build());
        return redirectToLogin;
    }
}
