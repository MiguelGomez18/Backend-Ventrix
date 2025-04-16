package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Restaurante;
import com.VenTrix.com.VenTrix.Entidades.Sucursal;
import com.VenTrix.com.VenTrix.Servicios.Sucursal_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class Sucursal_Controlador {

    // Aquí se crean los endpoints para las operaciones CRUD de la entidad Sucursal

    @Autowired
    private Sucursal_Servicio servicio;

    @PostMapping() // Crear una nueva sucursal
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal saveSucursal = servicio.addSucursal(sucursal);
        return new ResponseEntity<>(saveSucursal, HttpStatus.CREATED);
    }


    @GetMapping("/id_usuario/{administrador}") // Obtener un usuario por su correo
    public ResponseEntity<String> getUsuarioByDocumento(@PathVariable("administrador") String administrador) {
        String sucursal = servicio.getByAdministrador(administrador);
        return new ResponseEntity<>(sucursal, HttpStatus.OK);
    }

    @GetMapping("/{id_sucursal}") // Obtener una sucursal por su ID
    public ResponseEntity<Sucursal> obtenerSucursal(@PathVariable String id_sucursal) {
        Sucursal sucursal = servicio.getSucursalById(id_sucursal);
        return new ResponseEntity<>(sucursal, HttpStatus.OK);
    }

    @GetMapping("/restaurante/{id_restaurante}") // Obtener sucursales por id de restaurante
    public ResponseEntity<List<Sucursal>> obtenerSucursalesPorRestaurante(@PathVariable String id_restaurante) {
        List<Sucursal> sucursales = servicio.getSucursalesPorRestaurante(id_restaurante);
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    @GetMapping("/id_restaurante/{id_restaurante}") // Obtener restaurante por id de sucursal
    public ResponseEntity<String> obtenerRestaurantePorSucursal(@PathVariable String id_restaurante) {
        String restaurante = servicio.getRestaurantePorId(id_restaurante);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @PutMapping("/{id_sucursal}") // Actualizar una sucursal por su ID
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable String id_sucursal, @RequestBody Sucursal sucursal) {
        Sucursal updateSucursal = servicio.updateSucursal(id_sucursal, sucursal);
        return new ResponseEntity<>(updateSucursal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // Eliminar una sucursal por su ID
    public ResponseEntity<Void> eliminarSucursal(@PathVariable String id_sucursal) {
        servicio.deleteSucursal(id_sucursal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
