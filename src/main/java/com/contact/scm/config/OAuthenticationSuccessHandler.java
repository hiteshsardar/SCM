package com.contact.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.contact.scm.entities.ProviderConverter;
import com.contact.scm.entities.User;
import com.contact.scm.helpers.AppConstants;
import com.contact.scm.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    private final UserRepo userRepo;

    public OAuthenticationSuccessHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //verify the provider
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // save user data
        User user = null;
        String userEmail = "";

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            userEmail = oauthUser.getAttribute("email");
             user = User.builder()
            .userId(UUID.randomUUID().toString())
            .rolList(List.of(AppConstants.ROLE_USER))
            .emailVerified(true)
            .enabled(true)
            .email(userEmail)
            .name(oauthUser.getAttribute("name"))
            .profilePic(oauthUser.getAttribute("picture"))
            .provider(new ProviderConverter().convert("google"))
            .providerUserId(oauthUser.getName())
            .about("This account is created using google...")
            .password("dymmu")
            .build();
        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            userEmail = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email") : oauthUser.getAttribute("login") + "@gmail.com";
            user = User.builder()
            .userId(UUID.randomUUID().toString())
            .rolList(List.of(AppConstants.ROLE_USER))
            .emailVerified(true)
            .enabled(true)
            .email(userEmail)
            .name(oauthUser.getAttribute("login"))
            .profilePic(oauthUser.getAttribute("avatar_url"))
            .provider(new ProviderConverter().convert("github"))
            .providerUserId(oauthUser.getName())
            .about("This account is created using github...")
            .password("dymmu")
            .build();
        }else {
            logger.error("Unknow Provider");
        }
        
        User isUser = userRepo.findByEmail(userEmail).orElse(null);
        if(isUser == null) {
            userRepo.save(user);
            logger.info("User saved {}", userEmail);
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }



}
