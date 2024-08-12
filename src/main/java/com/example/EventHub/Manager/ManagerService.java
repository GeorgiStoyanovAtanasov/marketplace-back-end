package com.example.EventHub.Manager;

import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ManagerRepository managerRepository;
    public Integer getManagerId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        Manager manager = managerRepository.findByUser(user);
        return manager.getId();
    }
}
