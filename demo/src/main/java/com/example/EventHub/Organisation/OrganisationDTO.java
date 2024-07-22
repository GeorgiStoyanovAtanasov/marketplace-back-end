package com.example.EventHub.Organisation;

import lombok.Data;

@Data
public class OrganisationDTO {
    private Integer id;
    private String name;

    public OrganisationDTO() {
    }

    public OrganisationDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
