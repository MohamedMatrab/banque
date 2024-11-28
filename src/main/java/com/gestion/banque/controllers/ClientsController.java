package com.gestion.banque.controllers;

import com.gestion.banque.entity.Client;
import com.gestion.banque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("client", new Client());
        return "add_client";
    }

    @PostMapping("/save")
    public String add(@ModelAttribute("client") Client client, Model model, RedirectAttributes redirectAttributes) {
        try {
            clientService.saveClient(client);
            redirectAttributes.addFlashAttribute("message", "Client ajouté avec succès !");
            return "redirect:/clients";
        }
        catch (Exception e) {
            model.addAttribute("client", client);
            model.addAttribute("error", "Erreur : Impossible d'ajouter le client. Veuillez réessayer.");
            return "add_client";
        }
    }

    @GetMapping("")
    public String clientsList(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") String id, RedirectAttributes redirectAttributes)
    {
        try {
            clientService.deleteClient(id);
            redirectAttributes.addFlashAttribute("message", "Client supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : Impossible de supprimer le client.");
        }
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable("id") String id, Model model) {
        Client client = clientService.getById(id);
        model.addAttribute("client", client);
        return "edit_client";
    }

    @PostMapping("/update")
    public String updateClient(@ModelAttribute("client") Client client, RedirectAttributes redirectAttributes) {
        try {
            clientService.saveClient(client); // Update the client information
            redirectAttributes.addFlashAttribute("message", "Client mis à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : Impossible de mettre à jour le client.");
        }
        return "redirect:/clients"; // Redirect back to clients list
    }

}