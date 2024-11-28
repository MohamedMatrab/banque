package com.gestion.banque.services;

import com.gestion.banque.entity.Client;
import com.gestion.banque.entity.Transaction;
import com.gestion.banque.repository.ClientRepository;
import com.gestion.banque.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void addTransaction(String senderId, String receiverId, BigDecimal amount) {
        if(senderId.equals(receiverId))
            throw new IllegalArgumentException("L'expéditeur doit être différent du destinataire");

        Client sender = clientRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Client expéditeur non trouvé"));

        Client receiver = clientRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Client récepteur non trouvé"));

        if (sender.getSolde() < amount.doubleValue())
            throw new IllegalArgumentException("Solde insuffisant pour l'expéditeur.");

        sender.setSolde(sender.getSolde() - amount.doubleValue());
        receiver.setSolde(receiver.getSolde() + amount.doubleValue());

        clientRepository.save(sender);
        clientRepository.save(receiver);

        Transaction transaction = new Transaction(sender, receiver, amount);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getSentTransactions(String clientId) {
        return transactionRepository.findBySenderId(clientId);
    }

    public List<Transaction> getReceivedTransactions(String clientId) {
        return transactionRepository.findByReceiverId(clientId);
    }
}

