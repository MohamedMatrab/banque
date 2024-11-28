package com.gestion.banque.services;

import com.gestion.banque.entity.User;
import com.gestion.banque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Fonction pour ajouter un utilisateur
    public User addUser(User user) {
        return userRepository.save(user); // Enregistre l'utilisateur dans la base de données
    }

    // Fonction pour vérifier si un utilisateur existe par son ID
    public boolean checkIfUserExists(Long userId) {
        return userRepository.existsById(userId); // Simplification de la vérification de l'existence d'un utilisateur
    }

    // Fonction pour obtenir un utilisateur par son login
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Retourne un UserDetails de Spring Security avec les rôles de l'utilisateur
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())) // Convertir les rôles en autorités
                .build();
    }
}
