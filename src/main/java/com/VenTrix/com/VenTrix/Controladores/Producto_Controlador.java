package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Categoria;
import com.VenTrix.com.VenTrix.Entidades.Producto;
import com.VenTrix.com.VenTrix.Entidades.Sucursal;
import com.VenTrix.com.VenTrix.Repositorios.Categoria_Repositorio;
import com.VenTrix.com.VenTrix.Repositorios.Sucursal_Repositorio;
import com.VenTrix.com.VenTrix.Servicios.Producto_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class Producto_Controlador {

    private static final String DIRECTORIO_IMAGENES = "/app/imagenes/productos/";

    @Autowired
    private Producto_Servicio servicio;

    @Autowired
    private Categoria_Repositorio repositorio;

    @Autowired
    private Sucursal_Repositorio sucursalRepositorio;

    // Crear un nuevo producto
    @PostMapping("/registrar_producto")
    public ResponseEntity<Producto> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") float precio,
            @RequestParam("disponibilidad") boolean disponibilidad,
            @RequestParam("id_categoria") int categoriaId,
            @RequestParam("id_sucursal") String id_sucursal,
            @RequestParam("imagen") MultipartFile imagen) {

        try {
            String nombreArchivo = "h";

            Categoria categoria = repositorio.getReferenceById(categoriaId);
            Sucursal sucursal = sucursalRepositorio.getReferenceById(id_sucursal);

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setDisponibilidad(disponibilidad);
            producto.setSucursal(sucursal);
            producto.setImagen(nombreArchivo);
            producto.setCategoria(categoria);

            Producto nuevoProducto = servicio.guardarProducto(producto);
            if (!imagen.isEmpty()) {
                nombreArchivo = guardarImagenEnDirectorio(nuevoProducto.getId_producto(), imagen);
            }
            nuevoProducto.setImagen("/imagenes/productos/" + nuevoProducto.getId_producto() + "-" + nombreArchivo);
            nuevoProducto = servicio.actualizarProducto(nuevoProducto.getId_producto(), nuevoProducto);

            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String guardarImagenEnDirectorio(int id,MultipartFile imagen) throws IOException {
        String nombreArchivo = imagen.getOriginalFilename();
        File archivoDestino = new File(DIRECTORIO_IMAGENES + id + "-" + nombreArchivo);

        // Verifica si ya existe un archivo con el mismo nombre
        if (archivoDestino.exists()) {
            return nombreArchivo;
        }

        Files.copy(imagen.getInputStream(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return nombreArchivo;
    }


    // Obtener todos los productos
    @GetMapping()
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = servicio.listarProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/id_sucursal/{id_sucursal}")
    public ResponseEntity<List<Producto>> getIdproducto(@PathVariable("id_sucursal") String sucursal) {
        List<Producto> productos = servicio.getIdProductoById(sucursal);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/disponibilidad/{id_sucursal}")
    public ResponseEntity<List<Producto>> getIdproductodisponible(@PathVariable("id_sucursal") String sucursal) {
        boolean disponibilidad = true;
        List<Producto> productos = servicio.getIdProductoDisponibilidadById(sucursal, disponibilidad);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{id_sucursal}/{categoria}")
    public ResponseEntity<List<Producto>> getIdproductodisponible(@PathVariable("id_sucursal") String sucursal, @PathVariable("categoria") Integer categoria) {
        boolean disponibilidad = true;
        List<Producto> productos = servicio.getIdProductoCategoriaById(sucursal, disponibilidad, categoria);
        return ResponseEntity.ok(productos);
    }

    // Obtener los producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = servicio.obtenerProductoPorId(id);
        return producto != null ? new ResponseEntity<>(producto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id,
                                                       @RequestParam("nombre") String nombre,
                                                       @RequestParam("precio") Integer precio,
                                                       @RequestParam("id_categoria") int categoriaId,
                                                       @RequestParam("imagen") MultipartFile imagen,
                                                       @RequestParam("disponibilidad") boolean disponibilidad) {

        try {
            Producto productoExistente = servicio.obtenerProductoPorId(id);
            if (productoExistente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String nombreArchivo = productoExistente.getImagen();
            if (!imagen.isEmpty()) {

                if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                    File archivoAnterior = new File(DIRECTORIO_IMAGENES + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
                    if (archivoAnterior.exists()) {
                        archivoAnterior.delete();
                    }
                }

                nombreArchivo = guardarImagenEnDirectorio(id,imagen);
            }

            Categoria categoria = repositorio.getReferenceById(categoriaId);

            productoExistente.setNombre(nombre);
            productoExistente.setPrecio(precio);
            productoExistente.setCategoria(categoria);
            productoExistente.setDisponibilidad(disponibilidad);
            productoExistente.setImagen(nombreArchivo != null ? "/imagenes/productos/"+ id + "-" + nombreArchivo : null);

            Producto productoActualizado = servicio.actualizarProducto(id, productoExistente);
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        Producto producto = servicio.obtenerProductoPorId(id);
        String nombreArchivo = producto.getImagen();

        File archivoAnterior = new File(DIRECTORIO_IMAGENES + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1));
        if (archivoAnterior.exists()) {
            archivoAnterior.delete();
        }

        boolean eliminado = servicio.eliminarProducto(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}