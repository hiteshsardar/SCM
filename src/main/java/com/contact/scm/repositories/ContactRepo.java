/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.contact.scm.entities.Contact;
import com.contact.scm.entities.User;

@Repository
public interface ContactRepo extends MongoRepository<Contact, String> {

    // find the contact user
    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("{userId: ?0}")
    List<Contact> findUserId(String userId);

    Page<Contact> findByNameContainingAndUser(String nameKeyword, User user, Pageable pageable);
    Page<Contact> findByEmailContainingAndUser(String emailKeyword, User user, Pageable pageable);
    Page<Contact> findByPhoneNumberContainingAndUser(String phoneNoKeyword, User user, Pageable pageable);

}
