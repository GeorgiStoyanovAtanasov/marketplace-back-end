package com.example.EventHub;

import com.example.EventHub.Role.RoleRepository;
import com.example.EventHub.User.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @RequestMapping("/home")
    public String home() {
        return "home";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/profile")
    public String profile(){return "profile";}

}
