package com.contact.scm.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "social_links")
public class SocialLink {

    @Id
    private Long Id;
    private String link;
    private String title;
    @DBRef
    private Contact contact;
}
