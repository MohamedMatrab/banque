package com.gestion.banque.controllers;

import com.gestion.banque.entity.Client;
import com.gestion.banque.entity.Transaction;
import com.gestion.banque.repository.ClientRepository;
import com.gestion.banque.services.ClientService;
import com.gestion.banque.services.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.StandardEmitterMBean;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientRepository clientRepository;

    public String getTransactionHistory(String clientID) {
        return "clients";
    }

    @GetMapping("/new")
    public String showTransferForm(Model model,String senderId,String receiverId,BigDecimal amount)
    {
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("selectedSenderId", senderId);
        model.addAttribute("selectedReceiverId", receiverId);
        model.addAttribute("amount", amount);
        return "transfer";
    }

    @PostMapping("/save")
    public String processTransfer(@RequestParam String senderId,
                                  @RequestParam String receiverId,
                                  @RequestParam BigDecimal amount,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            transactionService.addTransaction(senderId, receiverId, amount);
            redirectAttributes.addFlashAttribute("message", "Transaction effectué avec succès !");
            return "redirect:/clients";
        }
        catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("clients", clientRepository.findAll());
            redirectAttributes.addAttribute("senderId", senderId);
            redirectAttributes.addAttribute("receiverId", receiverId);
            redirectAttributes.addAttribute("amount", amount);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transactions/new";
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("clients", clientRepository.findAll());
            redirectAttributes.addAttribute("senderId", senderId);
            redirectAttributes.addAttribute("receiverId", receiverId);
            redirectAttributes.addAttribute("amount", amount);
            redirectAttributes.addFlashAttribute("error", "Erreur : Impossible d'ajouter le client. Veuillez réessayer.");
            return "redirect:/transactions/new";
        }
    }

    @GetMapping("/{id}")
    public String showClientTransactions(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Client client = clientRepository.getReferenceById(id);
            if (client == null) {
                redirectAttributes.addFlashAttribute("error", "Client non trouvé !");
                return "redirect:/clients";
            }
            List<Transaction> sentTransactions = transactionService.getSentTransactions(id);
            List<Transaction> receivedTransactions = transactionService.getReceivedTransactions(id);

            model.addAttribute("client", client);
            model.addAttribute("sentTransactions", sentTransactions);
            model.addAttribute("receivedTransactions", receivedTransactions);
            return "transactions";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération des transactions !");
            return "redirect:/clients";
        }
    }
}

