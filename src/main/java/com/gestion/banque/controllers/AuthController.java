package com.gestion.banque.controllers;

import com.gestion.banque.entity.User;
import com.gestion.banque.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String index(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication);

        if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            System.out.println("hh h h h h h h h h h h h hhh h h hh h h h h h hhhhhhh hh h hhhhhhh h hh hh h h h hh h h  h h");
            return "redirect:/dashboard";
        }
        User user = new User();
        model.addAttribute("utilisateur", user);
        return "index";  // Assurez-vous que "index.html" existe dans le répertoire "src/main/resources/templates".
    }

    @PostMapping("/authentification")
    public String isUser(@ModelAttribute("utilisateur") User user, Model model) {
        try {
            // Création du token d'authentification avec les identifiants de l'utilisateur
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

            // Authentification de l'utilisateur
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Mise à jour du contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Affichage de l'authentification pour débogage
            System.out.println(authentication);

            // Vérification des rôles de l'utilisateur
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                System.out.println(authority.getAuthority());
            }

            // Vérification des rôles et redirection appropriée
            if (authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
                return "redirect:/dashboard";  // Redirige vers le dashboard pour ROLE_USER
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/clients";  // Redirige vers la page des clients pour ROLE_ADMIN
            }

            // Redirection par défaut si aucun des rôles n'est trouvé
            return "redirect:/";  // Page d'accueil ou autre par défaut

        } catch (Exception e) {
            // Gestion des erreurs : afficher un message d'erreur dans le modèle
            model.addAttribute("error", "Connexion échouée, les identifiants saisis sont incorrects.");
            return "index";  // Redirige vers la page de connexion en cas d'erreur
        }
    }

    @GetMapping("/dashboard")
    public String home() {
        return "dashboard";
    }

}