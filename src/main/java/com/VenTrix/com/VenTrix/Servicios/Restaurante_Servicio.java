package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Entidades.Estado_Restaurante;
import com.VenTrix.com.VenTrix.Entidades.Producto;
import com.VenTrix.com.VenTrix.Entidades.Restaurante;
import com.VenTrix.com.VenTrix.Entidades.Usuario;
import com.VenTrix.com.VenTrix.Repositorios.Restaurante_Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Restaurante_Servicio {

    @Autowired
    private Restaurante_Repositorio repositorio;

    //metodos para el CRUD

    public Restaurante addRestaurante(Restaurante restaurante){ //agregar un nuevo restaurante
        return repositorio.save(restaurante);
    }

    public String getIdByDocumentoUsuario(String documentoUsuario) {
        Restaurante restaurante = repositorio.findByUsuarioDocumento(documentoUsuario);
        return restaurante.getId();
    }

    public String getRestauranteByNombre(String id) { //obtener un restaurante por su id
       Restaurante restaurante = repositorio.findById(id).orElse(null);
        if (restaurante != null) {
            return restaurante.getNombre();
        } else {
            return null;
        }
    }

    public Restaurante getRestauranteById(String id) { //obtener un restaurante por su id
        return repositorio.findById(id).orElse(null);
    }

    public Restaurante updateRestaurante(String id_restaurante,Restaurante restaurante){ //actualizar un restaurante
        Restaurante restaurante1 = repositorio.findById(id_restaurante).orElse(null);
        if (restaurante1 != null) {
            restaurante1.setNombre(restaurante.getNombre());
            restaurante1.setDescripcion(restaurante.getDescripcion());
            restaurante1.setTelefono(restaurante.getTelefono());
            restaurante1.setDireccion(restaurante.getDireccion());
            restaurante1.setCorreo(restaurante.getCorreo());
            restaurante1.setImagen(restaurante.getImagen());
            repositorio.save(restaurante1);
        }
        return restaurante1;
    }

    public boolean deleteRestaurante(String id){ //eliminar un restaurante
        Restaurante restaurante = repositorio.findById(id).orElse(null);
        restaurante.setEstado(Estado_Restaurante.INACTIVO);
        repositorio.save(restaurante);
        return true;
    }

    public List<Restaurante> listar(){
        return repositorio.findAll();
    }


}
