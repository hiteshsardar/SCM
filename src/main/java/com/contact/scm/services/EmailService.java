/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
