package com.bitespeed.BiteSpeed.service;

import com.bitespeed.BiteSpeed.Repository.UserRepository;
import com.bitespeed.BiteSpeed.dto.UserDto;
import com.bitespeed.BiteSpeed.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.getUsers();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
