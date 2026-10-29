package com.Adrian.app.controller;

import com.Adrian.app.model.Producto;
import com.Adrian.app.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // GET: Permitido para ADMIN y USER (Ver catálogo de productos)
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return new ResponseEntity<>(productoRepository.findAll(), HttpStatus.OK);
    }

    // POST: Permitido solo para ADMIN (Crear productos)
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoRepository.save(producto), HttpStatus.CREATED);
    }

    // PUT: Permitido solo para ADMIN (Actualizar un producto existente)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        return productoRepository.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(detallesProducto.getNombre());
                    productoExistente.setPrecio(detallesProducto.getPrecio());
                    return new ResponseEntity<>(productoRepository.save(productoExistente), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // DELETE: Permitido solo para ADMIN (Eliminar un producto)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}