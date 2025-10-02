package com.bitespeed.BiteSpeed.controller;

import com.bitespeed.BiteSpeed.dto.UserDto;
import com.bitespeed.BiteSpeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDto = userService.getAllUsers();
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
