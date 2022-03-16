package com.hit.btvn_buoi4.controllers;

import com.hit.btvn_buoi4.model.User;
import com.hit.btvn_buoi4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("err", "");
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(
            @ModelAttribute("user") User user,
            Model model) {
        List<User> users = userRepository.findAll();
        if(users.contains(user)) {
            return "redirect:/";
        }
        model.addAttribute("err", "Tên đăng nhập hoặc mật khẩu không hợp lệ!");
        return "login";
    }

    @GetMapping(value = {"/", ""})
    public String getIndexPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/create")
    public String getCreateUser(Model model) {
        model.addAttribute("User", new User());
        return "create";
    }

    @PostMapping("/create")
    public String createNewUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String getDelete(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userRepository.getById(id));
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user){
        userRepository.deleteById(user.getId());
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String getEdit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userRepository.getById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        user.setId(user.getId());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setFullName(user.getFullName());
        userRepository.save(user);
        return "redirect:/";
    }
}
