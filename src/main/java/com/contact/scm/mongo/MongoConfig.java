/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.contact.scm")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    protected String getDatabaseName() {
        return "scm";
    }

}
