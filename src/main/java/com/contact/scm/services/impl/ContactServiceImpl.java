/***********************************************************************
 * Copyright (c) 2024
 * owned by Hitesh Sardar
 ***********************************************************************/

package com.contact.scm.services.impl;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.contact.scm.entities.Contact;
import com.contact.scm.entities.User;
import com.contact.scm.helpers.ResourceNotForndException;
import com.contact.scm.repositories.ContactRepo;
import com.contact.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepo contactRepo;

    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public Contact saveContact(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        Contact oldContactVal =  contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotForndException("Contact not found."));
        oldContactVal.setName(contact.getName());
        oldContactVal.setEmail(contact.getEmail());
        oldContactVal.setPhoneNumber(contact.getPhoneNumber());
        oldContactVal.setAddress(contact.getAddress());
        oldContactVal.setDescription(contact.getDescription());
        oldContactVal.setFavorite(contact.isFavorite());
        oldContactVal.setWebsiteLink(contact.getWebsiteLink());
        oldContactVal.setLinkedInLink(contact.getLinkedInLink());
        oldContactVal.setPicture(contact.getPicture());
        oldContactVal.setImagePublicId(contact.getImagePublicId());
        return contactRepo.save(oldContactVal);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactByID(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResolutionException("Contact not found by the given Id " + id));
    }

    @Override
    public void deleteContact(String id) {
        contactRepo.deleteById(id);
    }

    @Override
    public List<Contact> serachContactsByUserId(String userId) {
        return contactRepo.findUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> serachByName(String nameKeyword, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByNameContainingAndUser(nameKeyword, user, pageable);
    }

    @Override
    public Page<Contact> serachByEmail(String emailKeyword, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByEmailContainingAndUser(emailKeyword, user, pageable);
    }

    @Override
    public Page<Contact> serachByPhoneNo(String phoneNumberKeyword, int page, int size, String sortBy, String sortDirection, User user) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByPhoneNumberContainingAndUser(phoneNumberKeyword, user, pageable);
    }

}
