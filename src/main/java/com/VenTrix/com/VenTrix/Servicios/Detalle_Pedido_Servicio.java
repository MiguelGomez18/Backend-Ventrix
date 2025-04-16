package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Entidades.Detalle_Pedido;
import com.VenTrix.com.VenTrix.Entidades.Pedido;
import com.VenTrix.com.VenTrix.Repositorios.Detalle_Pedido_Repositorio;
import com.VenTrix.com.VenTrix.Repositorios.Pedido_Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Detalle_Pedido_Servicio {

    @Autowired
    private Detalle_Pedido_Repositorio detallePedidoRepositorio;

    @Autowired
    private Pedido_Repositorio pedidoPedidoRepositorio;

    // Crear un nuevo detalle de pedido
    public Detalle_Pedido crearDetallePedido(Detalle_Pedido detallePedido) {
        return detallePedidoRepositorio.save(detallePedido);
    }

    // Obtener todos los detalles de los pedidos
    public List<Detalle_Pedido> obtenerTodosLosDetalles() {
        return detallePedidoRepositorio.findAll();
    }

    public List<Detalle_Pedido> obtenerTodosLosDetallesPorSucursal(String sucursal) {
        return detallePedidoRepositorio.findBySucursal(sucursal);
    }

    // Obtener un detalle de pedido por ID
    public Detalle_Pedido obtenerDetallePorId(int id) {
        return detallePedidoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado con ID: " + id));
    }

    public List<Detalle_Pedido> obtenerDetallePorId_pedido(int id_pedido) {
        Pedido pedido = pedidoPedidoRepositorio.findById(id_pedido).orElse(null);;
        return detallePedidoRepositorio.findByPedido(pedido);
    }

    // Actualizar un detalle de pedido
    public Detalle_Pedido actualizarDetallePedido(int id, Detalle_Pedido detallePedido) {
        Optional<Detalle_Pedido> detalleExistente = detallePedidoRepositorio.findById(id);
        if (detalleExistente.isPresent()) {
            Detalle_Pedido detalleActualizado = detalleExistente.get();
            detalleActualizado.setEstado(detallePedido.getEstado());
            return detallePedidoRepositorio.save(detalleActualizado);
        } else {
            throw new RuntimeException("Detalle de pedido no encontrado con ID: " + id);
        }
    }

    // Eliminar un detalle de pedido por ID
    public void eliminarDetallePedido(int id) {
        if (detallePedidoRepositorio.existsById(id)) {
            detallePedidoRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("Detalle de pedido no encontrado con ID: " + id);
        }
    }


}
