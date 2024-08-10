package com.example.EventHub.Manager;

import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    public Manager toEntity(ManagerDTO managerDTO){
        Manager manager = new Manager();
        manager.setUser(managerDTO.getUser());
        manager.setOrganisation(managerDTO.getOrganisation());
        return manager;
    }
    public ManagerDTO toDTO(Manager manager){
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(manager.getId());
        managerDTO.setUser(manager.getUser());
        managerDTO.setOrganisation(manager.getOrganisation());
        return managerDTO;
    }
}
