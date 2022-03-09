package com.hit.btvn_buoi3;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @GetMapping("/users")
    public List<User> getList() {
        return Store.userList;
    }
}
