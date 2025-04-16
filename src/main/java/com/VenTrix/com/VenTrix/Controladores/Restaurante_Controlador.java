package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.*;
import com.VenTrix.com.VenTrix.Servicios.Restaurante_Servicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class Restaurante_Controlador {

    // Aquí se definiran las rutas para los endpoints del restaurantes
    private static final String DIRECTORIO_IMAGENES = "src/main/resources/imagenes/restaurantes/";

    @Autowired
    private Restaurante_Servicio servicio;

    @PostMapping() // Crear un nuevo restaurante
    public ResponseEntity<Restaurante> crearRestaurante(@RequestParam("id") String id,
                                                        @RequestParam("nombre") String nombre,
                                                        @RequestParam("descripcion") String descripcion,
                                                        @RequestParam("telefono") String telefono,
                                                        @RequestParam("direccion") String direccion,
                                                        @RequestParam("correo") String correo,
                                                        @RequestParam("imagen") MultipartFile imagen,
                                                        @RequestParam("fecha_creacion") LocalDate fecha_creacion,
                                                        @RequestParam("fecha_finalizacion") LocalDate fecha_finalizacion,
                                                        @RequestParam("estado") Estado_Restaurante estado,
                                                        @RequestParam("usuario") String usuarioJson) {
        try {
            String nombreArchivo = "h";

            ObjectMapper objectMapper = new ObjectMapper();
            Usuario usuario = objectMapper.readValue(usuarioJson, Usuario.class);

            Restaurante restaurante = new Restaurante();
            restaurante.setId(id);
            restaurante.setNombre(nombre);
            restaurante.setDescripcion(descripcion);
            restaurante.setTelefono(telefono);
            restaurante.setDireccion(direccion);
            restaurante.setCorreo(correo);
            restaurante.setImagen(nombreArchivo);
            restaurante.setFecha_creacion(fecha_creacion);
            restaurante.setFecha_finalizacion(fecha_finalizacion);
            restaurante.setEstado(estado);
            restaurante.setUsuario(usuario);

            Restaurante nuevoRestaurante = servicio.addRestaurante(restaurante);
            if (!imagen.isEmpty()) {
                nombreArchivo = guardarImagenEnDirectorio(nuevoRestaurante.getId(), imagen);
            }
            nuevoRestaurante.setImagen("/imagenes/restaurantes/" + nuevoRestaurante.getId() + "-" + nombreArchivo);
            nuevoRestaurante = servicio.updateRestaurante(nuevoRestaurante.getId(), nuevoRestaurante);

            return new ResponseEntity<>(nuevoRestaurante, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String guardarImagenEnDirectorio(String id,MultipartFile imagen) throws IOException {
        String nombreArchivo = imagen.getOriginalFilename();
        File archivoDestino = new File(DIRECTORIO_IMAGENES + id + "-" + nombreArchivo);

        if (archivoDestino.exists()) {
            return nombreArchivo;
        }

        Files.copy(imagen.getInputStream(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return nombreArchivo;
    }

    @GetMapping() // Listar todos los restaurantes
    public ResponseEntity<Iterable<Restaurante>> listarRestaurantes(){
        List<Restaurante> lista = servicio.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/nombre/{id}") // Obtener un restaurante por su id
    public ResponseEntity<String> getRestauranteByNombre(@PathVariable String id){
        String restaurante = servicio.getRestauranteByNombre(id);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @GetMapping("/{id}") // Obtener un restaurante por su id
    public ResponseEntity<Restaurante> getRestauranteById(@PathVariable String id){
        Restaurante restaurante = servicio.getRestauranteById(id);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    @GetMapping("/id_usuario/{id_usuario}")
    public ResponseEntity<String> getId_usuario(@PathVariable String id_usuario) {
        String id = servicio.getIdByDocumentoUsuario(id_usuario);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}") // Actualizar un restaurante por su id
    public ResponseEntity<Restaurante> updateRestaurante(@PathVariable String id,
                                                         @RequestParam("nombre") String nombre,
                                                         @RequestParam("descripcion") String descripcion,
                                                         @RequestParam("telefono") String telefono,
                                                         @RequestParam("direccion") String direccion,
                                                         @RequestParam("fecha_finalizacion") LocalDate fecha_finalizacion,
                                                         @RequestParam("correo") String correo,
                                                         @RequestParam("imagen") MultipartFile imagen) {
        try {
            Restaurante restauranteExistente = servicio.getRestauranteById(id);
            if (restauranteExistente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String nombreArchivo = restauranteExistente.getImagen();
            if (!imagen.isEmpty()) {

                if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                    File archivoAnterior = new File(DIRECTORIO_IMAGENES + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
                    if (archivoAnterior.exists()) {
                        archivoAnterior.delete();
                    }
                }

                nombreArchivo = guardarImagenEnDirectorio(id,imagen);
            }

            restauranteExistente.setNombre(nombre);
            restauranteExistente.setDescripcion(descripcion);
            restauranteExistente.setTelefono(telefono);
            restauranteExistente.setDireccion(direccion);
            restauranteExistente.setFecha_finalizacion(fecha_finalizacion);
            restauranteExistente.setCorreo(correo);
            restauranteExistente.setImagen(nombreArchivo != null ? "/imagenes/restaurantes/"+ id + "-" + nombreArchivo : null);

            Restaurante restauranteActualizado = servicio.updateRestaurante(id, restauranteExistente);
            return new ResponseEntity<>(restauranteActualizado, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}") // Eliminar un restaurante por su id
    public ResponseEntity<Void> deleteRestaurante(@PathVariable String id){
        Restaurante restaurante = servicio.getRestauranteById(id);
        String nombreArchivo = restaurante.getImagen();

        File archivoAnterior = new File(DIRECTORIO_IMAGENES + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
        if (archivoAnterior.exists()) {
            archivoAnterior.delete();
        }

        boolean eliminado = servicio.deleteRestaurante(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
