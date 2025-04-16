package com.VenTrix.com.VenTrix.Repositorios;

import com.VenTrix.com.VenTrix.Entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pedido_Repositorio extends JpaRepository<Pedido, Integer> {
    // Aquí puedes agregar consultas personalizadas si lo necesitas, por ejemplo:
    // List<Pedido> findByTotalPedidoGreaterThan(Float total);
}
