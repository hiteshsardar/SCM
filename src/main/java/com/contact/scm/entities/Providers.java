package com.contact.scm.entities;

public enum Providers {
    SELF("self"), 
    GOOGLE("google"), 
    GITHUB("github");

    private final String provider;

    Providers(final String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return provider;
    }
}
