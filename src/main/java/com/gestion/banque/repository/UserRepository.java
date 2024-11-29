package com.gestion.banque.repository;

import com.gestion.banque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode pour trouver un utilisateur par son login
    Optional<User> findByLogin(String login);
    User getByClientId(String clientId);
}
