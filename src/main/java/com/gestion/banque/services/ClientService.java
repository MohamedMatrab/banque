package com.gestion.banque.services;

import com.gestion.banque.entity.Client;
import com.gestion.banque.entity.Role;
import com.gestion.banque.entity.User;
import com.gestion.banque.repository.ClientRepository;
import com.gestion.banque.repository.RoleRepository;
import com.gestion.banque.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository,UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.userRepository=userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveClient(Client client) {
        Optional<Role> optionalRole2 = roleRepository.findByName("ROLE_USER");

        Role userRole = optionalRole2.orElseThrow(() -> new RuntimeException("Role not found"));
        String encodedPassword = passwordEncoder.encode(client.getNom()+"123@");

        clientRepository.save(client);

        User user = new User(client.getNom(), encodedPassword, List.of(userRole), client);

        userRepository.save(user);
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }
    public Client getById(String id) {
        return clientRepository.getReferenceById(id);
    }

}