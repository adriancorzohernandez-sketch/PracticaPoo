package com.Adrian.app.repository; // Ajusta el paquete

import com.Adrian.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Boot crea la consulta automáticamente solo con nombrar bien el método
    User findByNombre(String nombre);
}