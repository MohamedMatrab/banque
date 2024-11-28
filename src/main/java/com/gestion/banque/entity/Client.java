package com.gestion.banque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

@Entity
public class Client {
    @Id
    private String id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> receivedTransactions;

    private int age;
    private double solde;

    public Client() {
        this.id = UUID.randomUUID().toString();
    }

    public Client(String nom, String prenom, String email, double solde, int age) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.solde = solde;
        this.age = age;
    }

    // Getters and setters
    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
