package com.example.EventHub.Organisation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {
    Organisation findByName(String name);
    List<Organisation> findAllByOrganisationPermission(OrganisationPermission organisationPermission);
    @Query("SELECT o FROM Organisation o " +
            "WHERE (:name IS NULL OR o.name LIKE %:name%) " +
            "AND (:permission IS NULL OR o.organisationPermission = :permission)")
    List<Organisation> findByNameAndPermission(@Param("name") String name,
                                               @Param("permission") OrganisationPermission permission);
}
