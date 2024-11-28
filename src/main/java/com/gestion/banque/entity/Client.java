package com.gestion.banque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
public class Client {
    @Id
    private String id;  // ID de type GUID (UUID)

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    private String email;

    private double solde;

    // Constructeur par défaut : Génération automatique de l'ID
    public Client() {
        this.id = UUID.randomUUID().toString();
    }

    // Constructeur personnalisé
    public Client(String nom, String prenom, String email, double solde) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.solde = solde;
    }

    // Getters et Setters
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
}

