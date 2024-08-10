package com.example.EventHub.EventStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


public enum EventStatus {
    AVAILABLE,
    FULL,
    FINISHED
}
