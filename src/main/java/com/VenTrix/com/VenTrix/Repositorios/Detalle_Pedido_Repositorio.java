package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Detalle_Pedido;
import com.VenTrix.com.VenTrix.Entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Detalle_Pedido_Repositorio extends JpaRepository<Detalle_Pedido, Integer> {
    List<Detalle_Pedido> findByPedido(Pedido pedido);
    List<Detalle_Pedido> findBySucursal(String sucursal);
}
