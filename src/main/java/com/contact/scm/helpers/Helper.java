/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    public static String getEmailOfLogginUser(Authentication authentication) {

        if(authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String clientId = token.getAuthorizedClientRegistrationId();
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            if(clientId.equalsIgnoreCase("google"))
                return oauthUser.getAttribute("email");
            else if (clientId.equalsIgnoreCase("github"))
                return oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email") : oauthUser.getAttribute("login") + "@gmail.com";
        }
        return authentication.getName();
    }

    public static String getLinkForEmailVerification(String emailToken) {
        return "http://localhost:8089/auth/verify-email?token=" + emailToken;
    }
}
