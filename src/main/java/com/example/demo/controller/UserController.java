package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/")
    User register(@RequestBody User user){
        return repository.save(user);
    }

    @PostMapping("/")
    User login(@RequestBody UserDto userDto){
        return repository.findUserByUsernameAndPassword(userDto.getUsername(),
                userDto.getPassword());
    }

}
