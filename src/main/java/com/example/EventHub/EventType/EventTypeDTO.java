package com.example.EventHub.EventType;

public class EventTypeDTO {
    private Integer id;
    private String typeName;

    public EventTypeDTO() {
    }

    public EventTypeDTO(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

