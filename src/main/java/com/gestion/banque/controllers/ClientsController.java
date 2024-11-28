package com.gestion.banque.controllers;

import com.gestion.banque.entity.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    @GetMapping("/new")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("client", new Client());
        return "add_client";
    }

    @PostMapping()
    public String ajouterClient(Client client, Model model) {
        System.out.println("Client ajouté : " + client.getNom() + ", " + client.getEmail());
        model.addAttribute("message", "Client ajouté avec succès !");
        return "redirect:/clients/new"; // Redirige vers le formulaire
    }
}