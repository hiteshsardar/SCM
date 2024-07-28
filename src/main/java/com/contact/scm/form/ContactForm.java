package com.contact.scm.form;

import org.springframework.web.multipart.MultipartFile;
import com.contact.scm.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactForm {
    @NotBlank(message = "Name is required.")
    @Size(min = 3, message = "Minimum 3 character is required.")
    private String name;
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email address.[user@gmail.com]")
    private String email;
    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number.")
    private String phoneNumber;
    @NotBlank(message = "User Address is required.")
    private String address;
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;
    @ValidFile
    private MultipartFile contactImage;
    private String picture;
}
