package com.gestion.banque.DataInitializer;

import com.gestion.banque.entity.Role;
import com.gestion.banque.entity.User;
import com.gestion.banque.repository.RoleRepository;
import com.gestion.banque.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Injection des repositories via le constructeur
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Méthode d'initialisation exécutée au démarrage de l'application
    @Override
    public void run(String... args) throws Exception {
        // Vérifiez si des rôles existent, sinon les ajouter
        if (roleRepository.count() == 0) {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }

        // Vérifiez si des utilisateurs existent, sinon les ajouter
        if (userRepository.count() == 0) {
            Optional<Role> optionalRole = roleRepository.findByName("ROLE_ADMIN");
            Optional<Role> optionalRole2 = roleRepository.findByName("ROLE_USER");


            Role adminRole = optionalRole.orElseThrow(() -> new RuntimeException("Role not found"));
            Role userRole = optionalRole2.orElseThrow(() -> new RuntimeException("Role not found"));

            User admin = new User("admin", "admin123@", List.of(adminRole,userRole), null);

            userRepository.save(admin);
        }
    }
}
