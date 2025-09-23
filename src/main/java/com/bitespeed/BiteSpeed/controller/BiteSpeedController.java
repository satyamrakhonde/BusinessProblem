package com.bitespeed.BiteSpeed.controller;

import com.bitespeed.BiteSpeed.dto.ContactDto;
import com.bitespeed.BiteSpeed.dto.RequestDto;
import com.bitespeed.BiteSpeed.response.IdentifyResponse;
import com.bitespeed.BiteSpeed.service.BiteSpeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BiteSpeedController {

    @Autowired
    BiteSpeedService biteSpeedService;

    @PostMapping("/identity")
    public ResponseEntity<IdentifyResponse> createContact(@RequestBody RequestDto requestDto) {
        return new ResponseEntity<>(biteSpeedService.createContact(requestDto), HttpStatus.CREATED);
    }
}
