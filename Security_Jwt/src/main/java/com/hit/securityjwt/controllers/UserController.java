package com.hit.securityjwt.controllers;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.dto.UserDTO;
import com.hit.securityjwt.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/get")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(200).body(iUserService.getAllUser());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(200).body(iUserService.createUser(userDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("id") Integer id) {
        return ResponseEntity.status(200).body(iUserService.deleteUser(id));
    }
}
