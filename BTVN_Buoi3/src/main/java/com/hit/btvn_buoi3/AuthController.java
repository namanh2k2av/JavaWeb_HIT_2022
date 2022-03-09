package com.hit.btvn_buoi3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users")
    public String listLogin(@ModelAttribute("account") User user, Model model) {
        if(Store.checkAccount(user)) {
            model.addAttribute("list", Store.userList);
            return "users";
        }
        return "redirect:login";
    }
}
