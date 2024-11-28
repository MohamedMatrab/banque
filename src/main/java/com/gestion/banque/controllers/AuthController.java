package com.gestion.banque.controllers;

import com.gestion.banque.entity.User;
import com.gestion.banque.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        if (authentication.isAuthenticated()  && !(authentication.getPrincipal() instanceof String) ) {
            System.out.println("hh h h h h h h h h h h h hhh h h hh h h h h h hhhhhhh hh h hhhhhhh h hh hh h h h hh h h  h h");
            return  "redirect:/dashboard";
        }
        User user = new User();
        model.addAttribute("utilisateur", user);
        return "index";  // Assurez-vous que "index.html" existe dans le répertoire "src/main/resources/templates".
    }

    @PostMapping("/authentification")
    public String isUser(@ModelAttribute("utilisateur") User user, Model model) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(authentication);
            return "redirect:/dashboard";  // Redirige vers la page d'accueil en cas de succès
        } catch (Exception e) {
            model.addAttribute("error", "Connexion échouée, les identifiants saisis sont incorrects.");
            return "index";  // Reste sur la page de connexion en cas d'erreur
        }
    }


    @GetMapping("/dashboard")
    public String home() {
        return "dashboard";
    }

}
