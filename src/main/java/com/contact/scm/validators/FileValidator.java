/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.validators;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; //5MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 5MB.").addConstraintViolation();
            return false;
        }

        return true;
    }

}
