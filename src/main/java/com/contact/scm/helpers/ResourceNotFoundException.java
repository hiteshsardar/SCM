/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.helpers;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){
        super("Resources not Found");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
