package com.gestion.banque.services;

import com.gestion.banque.entity.User;
import com.gestion.banque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Fonction pour ajouter un utilisateur
    public User addUser(User user) {
        return userRepository.save(user); // Enregistre l'utilisateur dans la base de données
    }

    // Fonction pour vérifier si un utilisateur existe par son ID
    public boolean checkIfUserExists(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent(); // Retourne true si l'utilisateur existe, sinon false
    }

    // Fonction pour obtenir un utilisateur par son login
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
