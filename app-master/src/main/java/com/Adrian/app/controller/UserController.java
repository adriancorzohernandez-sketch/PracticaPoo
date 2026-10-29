package com.Adrian.app.controller;

import com.Adrian.app.model.User;
import com.Adrian.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserRepository usuarioRepository;

    // POST: Permitido para ADMIN y USER (Crear nuevos usuarios)
    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User usuario) {
        return new ResponseEntity<>(usuarioRepository.save(usuario), HttpStatus.CREATED);
    }

    // GET: Permitido solo para ADMIN (Listar usuarios)
    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
    }

    // PUT: Permitido solo para ADMIN (Actualizar un usuario existente)
    @PutMapping("/{id}")
    public ResponseEntity<User> actualizarUsuario(@PathVariable Long id, @RequestBody User detallesUsuario) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombre(detallesUsuario.getNombre());
                    usuarioExistente.setContrasena(detallesUsuario.getContrasena());
                    usuarioExistente.setRfc(detallesUsuario.getRfc());
                    usuarioExistente.setRol(detallesUsuario.getRol());
                    return new ResponseEntity<>(usuarioRepository.save(usuarioExistente), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE: Permitido solo para ADMIN (Eliminar un usuario)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}