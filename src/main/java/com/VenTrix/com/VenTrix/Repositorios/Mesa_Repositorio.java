package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Estado_Mesa;
import com.VenTrix.com.VenTrix.Entidades.Mesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface Mesa_Repositorio extends JpaRepository<Mesa, String> {
    List<Mesa> findBySucursalId(String id_sucursal);
    List<Mesa> findBySucursalIdAndEstado(String id_sucursal, Estado_Mesa estado);
}
