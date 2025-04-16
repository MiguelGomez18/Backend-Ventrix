package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Categoria;
import com.VenTrix.com.VenTrix.Entidades.Mesa;
import com.VenTrix.com.VenTrix.Servicios.Categoria_Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class Categoria_Controlador {

    // Aquí se crean los endpoints para las operaciones CRUD de la entidad Categoria

    @Autowired
    private Categoria_Servicio servicio;

    // Aquí se crea el endpoint para crear una nueva categoria
    @PostMapping()
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria saveCategoria = servicio.addCategoria(categoria);
        return new ResponseEntity<>(saveCategoria, HttpStatus.CREATED);
    }

    // Aquí se crea el endpoint para obtener todas las categorias
    @GetMapping()
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> lista = servicio.listarCategorias();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Aquí se crea el endpoint para obtener una categoria por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Integer id) {
        Categoria categoria = servicio.getCategoriaById(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @GetMapping("/id_sucursal/{id_sucursal}")
    public ResponseEntity<List<Categoria>> listarCategoriassucursales(@PathVariable("id_sucursal") String id_sucursal) {
        List<Categoria> lista = servicio.getSucursalById(id_sucursal);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Aquí se crea el endpoint para actualizar una categoria
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        Categoria updateCategoria = servicio.updateCategoria(id, categoria);
        return new ResponseEntity<>(updateCategoria, HttpStatus.OK);
    }

    // Aquí se crea el endpoint para eliminar una categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        servicio.deleteCategoria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}









