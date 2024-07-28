/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.contact.scm.entities.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmailToken(String emailToken);
}
