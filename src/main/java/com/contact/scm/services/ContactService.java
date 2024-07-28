/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.contact.scm.entities.Contact;
import com.contact.scm.entities.User;

public interface ContactService {

    // save contact
    Contact saveContact(Contact contact);

    // update contact
    Contact updateContact(Contact contact);

    //get contact
    List<Contact> getAllContacts();

    // get contact by ID
    Contact getContactByID(String id);

    // delete contact
    void deleteContact(String id);

    // search contacts by name
    Page<Contact> serachByName(String nameKeyword, int page, int size,String sortBy, String sortDirection, User user);

    // search contacts by email
    Page<Contact> serachByEmail( String emailKeyword, int page, int size,String sortBy, String sortDirection, User user);

    // search contacts by contact no
    Page<Contact> serachByPhoneNo(String phoneNumberKeyword, int page, int size,String sortBy, String sortDirection, User user);

    // search contacts by user id
    List<Contact> serachContactsByUserId(String userId);

    // List of contacts
    Page<Contact> getByUser(User user, int page, int size,String sortBy, String sortDirection);
}
