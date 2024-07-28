/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.helpers;

public class ResourceNotForndException extends RuntimeException {

    public ResourceNotForndException(){
        super("Resources not Found");
    }

    public ResourceNotForndException(String msg) {
        super(msg);
    }

}
