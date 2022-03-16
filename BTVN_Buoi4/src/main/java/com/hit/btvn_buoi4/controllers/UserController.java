package com.hit.btvn_buoi4.controllers;

import com.hit.btvn_buoi4.dto.UserDTO;
import com.hit.btvn_buoi4.exception.NotFoundException;
import com.hit.btvn_buoi4.model.User;
import com.hit.btvn_buoi4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
           return ResponseEntity.status(200).body("Không tìm thấy");
        }
        return ResponseEntity.status(200).body(optionalUser.get());
    }
}
