/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services;

import java.util.List;
import java.util.Optional;
import com.contact.scm.entities.User;


public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String userId);
    Optional<User> updateUser(User user);
    void deleteUser(String userId);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
