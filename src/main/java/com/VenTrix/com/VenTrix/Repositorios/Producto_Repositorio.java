package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Producto_Repositorio extends JpaRepository<Producto,Integer>{
    List<Producto> findBySucursalId(String id_sucursal);
    List<Producto> findBySucursalIdAndDisponibilidad(String id_sucursal, boolean disponibilidad);
    List<Producto> findBySucursalIdAndDisponibilidadAndCategoriaId(String id_sucursal, boolean disponibilidad, Integer id_categoria);
}