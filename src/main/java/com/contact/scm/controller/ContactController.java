/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.contact.scm.entities.Contact;
import com.contact.scm.entities.User;
import com.contact.scm.form.ContactForm;
import com.contact.scm.form.ContactSearchForm;
import com.contact.scm.helpers.AppConstants;
import com.contact.scm.helpers.Helper;
import com.contact.scm.helpers.Message;
import com.contact.scm.helpers.MessageType;
import com.contact.scm.services.ContactService;
import com.contact.scm.services.ImageService;
import com.contact.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;
    private final UserService userService;
    private final ImageService imageService;

    public ContactController(ContactService contactService, UserService userService, ImageService imageService) {
        this.contactService = contactService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {

        // validate the form data
        if(result.hasErrors()){
            // add message
            Message message = Message.builder().content("Please resolve the following errors !!!").type(MessageType.red).build();
            session.setAttribute("message", message);
            return "user/add_contact";
        }

        String userName = Helper.getEmailOfLogginUser(authentication);
        User user = userService.getUserByEmail(userName);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavorite(contactForm.isFavorite());
        if ((contactForm.getContactImage() != null) && (!contactForm.getContactImage().isEmpty())) {
            String fileName = UUID.randomUUID().toString();
            String fileUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            contact.setPicture(fileUrl);
            contact.setImagePublicId(fileName);
        }
        contact.setUser(user);
        
        Contact savedContact = contactService.saveContact(contact);
        
        // add message
        Message message = Message.builder().content("Successfully added the contact of " + savedContact.getName()).type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts/add";
    }

    @GetMapping
    public String viewContacts(
        @RequestParam(value = "page", defaultValue = "0") int page, 
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size, 
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model, Authentication authentication) {
        String loginUserEmail = Helper.getEmailOfLogginUser(authentication);
        User user = userService.getUserByEmail(loginUserEmail);

        Page<Contact> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/view_contacts";
    }

    @GetMapping("/search")
    public String serachHandler(
        @RequestParam(value = "page", defaultValue = "0") int page, 
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size, 
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, 
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        @ModelAttribute ContactSearchForm contactSearchForm,
        Model model, Authentication authentication) {

        User user = userService.getUserByEmail(Helper.getEmailOfLogginUser(authentication));

        Page<Contact> pageContacts = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContacts = contactService.serachByName(contactSearchForm.getKeyword(), page, size, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContacts = contactService.serachByEmail(contactSearchForm.getKeyword(), page, size, sortBy, direction, user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContacts = contactService.serachByPhoneNo(contactSearchForm.getKeyword(), page, size, sortBy, direction,user);
        }

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", contactSearchForm);
        return "user/search";
    }

    // delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session) {
        contactService.deleteContact(contactId);
        logger.info("Contact deleted of contactid {}.", contactId);

        // delete message
        Message message = Message.builder().content("Contact has deleted successfully. ").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts";
    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactView(@PathVariable String contactId, Model model) {
        Contact contact = contactService.getContactByID(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }

    // update contact
    @PostMapping("/update/{contactId}")
    public String updateContact(
        @PathVariable String contactId,
        @Valid @ModelAttribute ContactForm contactForm, 
        BindingResult result, HttpSession session) {

        // validate the form data
        if(result.hasErrors()){
            // add message
            Message message = Message.builder().content("Please resolve the following errors !!!").type(MessageType.red).build();
            session.setAttribute("message", message);
            return "user/update_contact_view";
        }

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());

        if ((contactForm.getContactImage() != null) && (!contactForm.getContactImage().isEmpty())) {
            // process image
            String fileNmae = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileNmae);
            contact.setImagePublicId(fileNmae);
            contact.setPicture(imageUrl);
        } else {
            Contact oldContact = contactService.getContactByID(contactId);
            contact.setImagePublicId(oldContact.getImagePublicId());
            contact.setPicture(oldContact.getPicture());
        }

        Contact updateContact = contactService.updateContact(contact);

        // delete message
        Message message = Message.builder().content("Contact details for " + updateContact.getName() + "has updated successfully.").type(MessageType.yellow).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts";
    }
}
