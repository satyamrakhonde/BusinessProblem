package com.bitespeed.BiteSpeed.service;

import com.bitespeed.BiteSpeed.Repository.ContactRepository;
import com.bitespeed.BiteSpeed.dto.RequestDto;
import com.bitespeed.BiteSpeed.entity.Contact;
import com.bitespeed.BiteSpeed.enumm.LinkPrecedence;
import com.bitespeed.BiteSpeed.response.ContactResponse;
import com.bitespeed.BiteSpeed.response.IdentifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BiteSpeedService {

    @Autowired
    ContactRepository contactRepository;


    public IdentifyResponse createContact(RequestDto requestDto) {

        String email = requestDto.getEmail();
        String phone = String.valueOf(requestDto.getPhoneNumber());

        List<Contact> matches = contactRepository.findByEmailOrPhoneNumber(email, phone);

        Contact primaryContact;

        if(matches.isEmpty()) {
            primaryContact = Contact.builder()
                    .email(email)
                    .phoneNumber(phone)
                    .linkPrecedence(LinkPrecedence.PRIMARY)
                    .build();
            contactRepository.save(primaryContact);
        } else {
            //At least one match -> get Oldest primay
            primaryContact = matches.stream()
                    .filter(e -> e.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                    .min(Comparator.comparing(Contact::getCreatedAt))
                    .orElse(matches.get(0));

            //Check if new info not already present
            boolean alreadyExist = matches.stream()
                    .anyMatch(c ->
                            Objects.equals(c.getEmail(), email) &&
                            Objects.equals(c.getPhoneNumber(), phone));

            if(!alreadyExist) {
                Contact secondary = Contact.builder()
                        .email(email)
                        .phoneNumber(phone)
                        .linkedId(Math.toIntExact(primaryContact.getId()))
                        .linkPrecedence(LinkPrecedence.SECONDARY)
                        .build();
                contactRepository.save(secondary);
                matches.add(secondary);
            }

            //If there are other primaries , demote them
            for(Contact c : matches) {
                if(!c.getId().equals(primaryContact.getId()) &&
                    c.getLinkPrecedence() == LinkPrecedence.PRIMARY) {
                    c.setLinkPrecedence(LinkPrecedence.SECONDARY);
                    c.setLinkedId(Math.toIntExact(primaryContact.getId()));
                    contactRepository.save(c);
                }
            }

        }
        //Consolidate response

        List<Contact> all = contactRepository.findByEmailOrPhoneNumber(email, phone);

        Set<String> emails = all.stream()
                .map(Contact::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> phones = all.stream()
                .map(Contact::getPhoneNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<Long> secondaryIds = all.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .map(Contact::getId)
                .toList();

        IdentifyResponse response = new IdentifyResponse();
        ContactResponse cr = new ContactResponse();
        cr.setPrimaryContactId(primaryContact.getId());
        cr.setEmails(new ArrayList<>(emails));
        cr.setPhoneNumbers(new ArrayList<>(phones));
        cr.setSecondaryContactIds(secondaryIds);

        response.setContact(cr);
        return response;
    }
}
