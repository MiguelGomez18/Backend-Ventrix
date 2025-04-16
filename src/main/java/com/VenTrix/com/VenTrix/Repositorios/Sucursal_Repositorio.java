package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sucursal_Repositorio extends JpaRepository<Sucursal, String> {

    // Metodo para obtener la sucursal por el administrador (correo o id del restaurante)
    Sucursal findByAdministrador(String administrador);

    // Metodo para obtener las sucursales por el id del restaurante
    List<Sucursal> findByRestauranteId(String idRestaurante);
}
