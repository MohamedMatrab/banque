package com.gestion.banque.services;

import com.gestion.banque.entity.Client;
import com.gestion.banque.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
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
