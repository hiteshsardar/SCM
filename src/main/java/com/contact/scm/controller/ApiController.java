/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.scm.entities.Contact;
import com.contact.scm.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final ContactService contactService;

    public ApiController(ContactService contactService) {
        this.contactService = contactService;
    }


    // get contact of a user
    @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        return contactService.getContactByID(contactId);
    }

    
}
