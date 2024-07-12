package com.example.EventHub.User;

import com.example.EventHub.WebSecurityConfig;
import com.example.EventHub.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    WebSecurityConfig webSecurityConfig;
    @Autowired
    RoleRepository roleRepository;
    public User toEntity(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(userDTO.getPassword()));
        user.setRole(roleRepository.findById(1).orElse(null));
        return user;
    }
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                null, null
        );
    }
}
