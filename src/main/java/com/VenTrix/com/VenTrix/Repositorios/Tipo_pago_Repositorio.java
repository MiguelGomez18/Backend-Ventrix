package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Tipo_pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tipo_pago_Repositorio extends JpaRepository<Tipo_pago, Integer> {
    List<Tipo_pago> findBySucursal(String id_sucursal);
}
