package com.bitespeed.BiteSpeed.entity;

import com.bitespeed.BiteSpeed.enumm.LinkPrecedence;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "contacts")
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String email;

    private int linkedId;

    private LinkPrecedence linkPrecedence;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;
}
