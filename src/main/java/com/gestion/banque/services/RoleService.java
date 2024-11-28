package com.gestion.banque.services;

import com.gestion.banque.entity.Role;
import com.gestion.banque.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Méthode pour ajouter un rôle
    public Role addRole(Role role) {
        // Vérifier si le rôle existe déjà par son nom
        Optional<Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isPresent()) {
            throw new IllegalArgumentException("Le rôle " + role.getName() + " existe déjà.");
        }

        // Sauvegarder le nouveau rôle
        return roleRepository.save(role);
    }

    // Méthode pour trouver un rôle par son nom
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Méthode pour récupérer tous les rôles
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
