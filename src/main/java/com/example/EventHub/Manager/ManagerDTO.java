package com.example.EventHub.Manager;

import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.User.User;
import lombok.Data;

@Data
public class ManagerDTO {
    private Integer id;
    private Organisation organisation;
    private User user;
}
