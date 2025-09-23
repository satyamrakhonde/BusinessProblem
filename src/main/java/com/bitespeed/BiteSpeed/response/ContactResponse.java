package com.bitespeed.BiteSpeed.response;

import lombok.Data;

import java.util.List;

@Data
public class ContactResponse {

    private Long primaryContactId; //id of the primary contact
    private List<String> emails; //all unique emails(first is primary email)
    private List<String> phoneNumbers; //all unique phoneNumbers
    private List<Long> secondaryContactIds; //ids of all secondary contacts
}
