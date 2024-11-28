package com.gestion.banque.repository;

import com.gestion.banque.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Méthode pour rechercher un rôle par son nom
    Optional<Role> findByName(String name);
}