package com.example.EventHub.User;

import com.example.EventHub.Role.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/registration")
    public String addUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles", roleRepository.findAll());
        return "registration";
    }

    @PostMapping("/registration/submit")
    public String postRegister(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "registration";
        }
        User userForSeeIfUsernameExist = userRepository.getUserByUsername(userDTO.getUsername());
        if (userForSeeIfUsernameExist != null) {
            model.addAttribute("userExistMessage", "This username already exists!");
            model.addAttribute("roles", roleRepository.findAll());
            return "registration";
        }

        User userForSeeIfEmailExist = userRepository.getUserByEmail(userDTO.getEmail());
        if (userForSeeIfEmailExist != null) {
            model.addAttribute("emailExistMessage", "This email already exists!");
            model.addAttribute("roles", roleRepository.findAll());
            return "registration";
        }

        if (!userService.ifTwoPasswordsMatch(userDTO.getPassword(), userDTO.getConfirmPassword())) {
            model.addAttribute("passwordsDoNotMatch", "Passwords do not match!");
            model.addAttribute("roles", roleRepository.findAll());
            return "registration";
        }
        User user = userMapper.toEntity(userDTO);
        model.addAttribute("user", user);
        userRepository.save(user);
        return "login";
    }
}
