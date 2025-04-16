package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Entidades.Pedido;
import com.VenTrix.com.VenTrix.Repositorios.Pedido_Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Pedido_Servicio {

    @Autowired
    private Pedido_Repositorio pedidoRepositorio;

    // Crear un nuevo pedido
    public int crearPedido(Pedido pedido) {
        pedidoRepositorio.save(pedido);
        return pedido.getId_pedido();
    }

    // Actualizar un pedido existente
    public Pedido actualizarPedido(int id, Pedido pedido) {
        Optional<Pedido> pedidoExistente = pedidoRepositorio.findById(id);
        if (pedidoExistente.isPresent()) {
            Pedido pedidoActualizado = pedidoExistente.get();
            pedidoActualizado.setFecha_pedido(pedido.getFecha_pedido());
            pedidoActualizado.setHora_pedido(pedido.getHora_pedido());
            pedidoActualizado.setTotal_pedido(pedido.getTotal_pedido());
            pedidoActualizado.setMesa(pedido.getMesa());
            pedidoActualizado.setTipo_pago(pedido.getTipo_pago());
            return pedidoRepositorio.save(pedidoActualizado);
        } else {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
    }

    // Obtener un pedido por su ID
    public Pedido obtenerPedidoPorId(int id) {
        return pedidoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepositorio.findAll();
    }

    // Eliminar un pedido por su ID
    public void eliminarPedido(int id) {
        if (pedidoRepositorio.existsById(id)) {
            pedidoRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
    }
}
