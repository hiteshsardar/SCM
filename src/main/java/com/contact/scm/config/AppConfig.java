/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudnary.api.secret}")
    private String apiScret;

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary (
            ObjectUtils.asMap("cloud_name", cloudName, "api_key", apiKey, "api_secret", apiScret )
        );
    }
}
