package com.contact.scm.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
    @Size(min = 3, message = "Minimum 3 character is required.")
    private String name;

    @Email(message = "Invalid email address.")
    private String email;

    @Size(min = 8, max = 12, message = "Invalid phone number.")
    @Pattern(regexp = "[0-9]*", message = "Charachers are not allowed in mobile no.")
    private String phoneNumber;

    @Size(min = 8, max = 20, message = "Password must be 8 to 20 characters.")
    private String password;
    private String about;
}