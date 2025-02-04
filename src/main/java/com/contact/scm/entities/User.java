/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String userId;
    private String name;
    @Indexed(unique = true)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;
    @Getter(AccessLevel.NONE)
    @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private boolean emailVerified = false;
    @Builder.Default
    private boolean phoneVerified = false;

    // SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB
    private Providers provider;
    private String providerUserId;
    private String emailToken;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolList = new ArrayList<>();

    @Builder.Default
    @DBRef
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolList.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
