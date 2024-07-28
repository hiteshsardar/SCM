/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "contacts")
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String picture;
    private String description;
    private String address;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;
    private String imagePublicId;
    @DBRef
    private User user;
    @DBRef
    private List<SocialLink> links = new ArrayList<>();
}
