package com.gestion.banque.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")  //
    private Client sender;

    @ManyToOne  // Many transactions can belong to one receiver
    @JoinColumn(name = "receiver_id")  // Specify the foreign key column name
    private Client receiver;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String status;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(Client sender, Client receiver, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.status = "Pending";
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Client getReceiver() {
        return receiver;
    }

    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
