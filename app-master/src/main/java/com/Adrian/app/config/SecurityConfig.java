package com.Adrian.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User; // Importación crítica de Spring Security
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Las rutas públicas van al principio
                        .requestMatchers("/", "/index.html", "/catalogo.html", "/admin.html", "/style.css", "/adminC.css", "/indexC.css", "/catalogoC.css").permitAll()

                        // Reglas para Productos
                        .requestMatchers(HttpMethod.GET, "/api/productos").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/productos/**").hasRole("ADMIN")

                        // Reglas para Usuarios
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        // Todo lo demás bloqueado
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123") // {noop} es obligatorio para que acepte texto plano
                .roles("ADMIN")
                .build();

        UserDetails usuario = User.builder()
                .username("usuario")
                .password("{noop}user123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, usuario);
    }
}