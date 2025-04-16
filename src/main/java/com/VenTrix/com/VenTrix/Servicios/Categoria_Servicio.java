package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Entidades.Activo;
import com.VenTrix.com.VenTrix.Entidades.Categoria;
import com.VenTrix.com.VenTrix.Entidades.Producto;
import com.VenTrix.com.VenTrix.Repositorios.Categoria_Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class Categoria_Servicio {

    @Autowired
    private Categoria_Repositorio repositorio;

    // Métodos de la clase

    public Categoria_Servicio(Categoria_Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Categoria addCategoria(Categoria categoria) { // Agregar una nueva categoría
        return repositorio.save(categoria);
    }

    public Categoria getCategoriaById(Integer id) { // Obtener una categoría por su ID
        return repositorio.findById(id).orElse(null);
    }

    public Categoria updateCategoria(Integer id, Categoria categoria) { // Actualizar una categoría
        return repositorio.save(categoria);
    }

    public void deleteCategoria(Integer id) { // Eliminar una categoría
        Categoria categoria = repositorio.findById(id).orElse(null);
        categoria.setActivo(Activo.INACTIVO);
        repositorio.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return repositorio.findAll();
    }

    public List<Categoria> getSucursalById(String id_sucursal) {
        return repositorio.findBySucursal(id_sucursal);
    }
}
