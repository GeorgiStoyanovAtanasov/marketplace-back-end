package com.example.EventHub.Manager;

import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.Organisation.OrganisationDTO;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserDTO;
import lombok.Data;

@Data
public class ManagerDTO {
    private Integer id;
    private Organisation organisation;
    private User user;
}
