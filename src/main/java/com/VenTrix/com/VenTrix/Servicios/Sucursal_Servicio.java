package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Entidades.*;
import com.VenTrix.com.VenTrix.Repositorios.Restaurante_Repositorio;
import com.VenTrix.com.VenTrix.Repositorios.Sucursal_Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sucursal_Servicio {

    @Autowired
    private Sucursal_Repositorio repositorio;


    //metodos de la clase

    public Sucursal addSucursal(Sucursal sucursal){ //agregar sucursal
        return repositorio.save(sucursal);
    }

    public List<Sucursal> listar(){ //listar todas las sucursales
        return repositorio.findAll();
    }

    public Sucursal getSucursalById(String id){ //buscar sucursal por id
        return repositorio.findById(id).orElse(null);
    }

    public String getByAdministrador(String administrador) { // Obtener un usuario por su correo
        Sucursal sucursal = repositorio.findByAdministrador(administrador);
        if (sucursal != null) {
            return sucursal.getId();
        } else {
            return null;
        }
    }

    public String getRestaurantePorId(String id) { // Obtener un sucursal por su id
        Sucursal sucu = repositorio.findById(id).orElse(null);
        if (sucu != null) {
            return sucu.getRestaurante().getId();
        } else {
            return null;
        }
    }

    public Sucursal updateSucursal(String id, Sucursal sucursal) { // actualizar sucursal
        if (repositorio.existsById(id)) {
            Sucursal sucu = repositorio.findById(id).orElse(null);
            sucursal.setRestaurante(sucu.getRestaurante());
            return repositorio.save(sucursal);
        } else {
            return null;
        }
    }

    public void deleteSucursal(String id){ //eliminar sucursal
        Sucursal sucursal = repositorio.findById(id).orElse(null);
        sucursal.setEstado(Estado_Sucursal.INACTIVO);
        repositorio.save(sucursal);
    }

    public List<Sucursal> getSucursalesPorRestaurante(String idRestaurante) {
        return repositorio.findByRestauranteId(idRestaurante); // Metodo para consultar las sucursales por restaurante
    }


}
