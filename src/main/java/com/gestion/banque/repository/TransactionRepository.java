package com.gestion.banque.repository;

import com.gestion.banque.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findBySenderId(String clientId);
    List<Transaction> findByReceiverId(String clientId);
}