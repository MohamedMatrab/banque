package com.gestion.banque.config;

import com.gestion.banque.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Configure the PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password hashing
    }

    // Define the AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws  Exception {
     return authenticationConfiguration.getAuthenticationManager();
    }

    // Configure HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/authentification","/static/**", "/images/**").permitAll()  // Public pages
                        .anyRequest().authenticated()  // All other pages require authentication
                )
                .formLogin((form) -> form
                        .loginPage("/")
                        .defaultSuccessUrl("/clients", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")  // Redirection après déconnexion
                .invalidateHttpSession(true)
                .clearAuthentication(true) // Supprimer l'authentification
                .permitAll()).rememberMe(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Assure la gestion de session
                );

        ;

        http.cors(c->c.disable());
        http.csrf(c->c.disable());
        http.headers(c->c.disable());


        return http.getOrBuild();  // Use getOrBuild() instead of build() to avoid deprecation warning
    }
}
