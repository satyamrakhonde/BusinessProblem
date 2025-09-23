package com.bitespeed.BiteSpeed.dto;

import com.bitespeed.BiteSpeed.enumm.LinkPrecedence;
import lombok.Data;

import java.util.Date;

@Data
public class ContactDto {
    private Long id;
    private String phoneNumber;
    private String email;
    private int linkedId;
    private LinkPrecedence linkPrecedence;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
