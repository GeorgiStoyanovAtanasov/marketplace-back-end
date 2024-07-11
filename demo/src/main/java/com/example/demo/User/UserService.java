package com.example.EventHub.User;

import com.example.EventHub.Event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public boolean ifTwoPasswordsMatch(String pass1, String pass2){
        return pass1.equals(pass2);
    }

}
