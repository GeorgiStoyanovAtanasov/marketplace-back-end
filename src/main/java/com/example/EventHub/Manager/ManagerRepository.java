package com.example.EventHub.Manager;

import com.example.EventHub.User.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Manager findByUser(User user);
}
