package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Detalle_Pedido;
import com.VenTrix.com.VenTrix.Servicios.Detalle_Pedido_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles-pedido")
public class Detalle_Pedido_Controlador {

    @Autowired
    private Detalle_Pedido_Servicio detallePedidoServicio;

    @PostMapping
    public Detalle_Pedido crearDetallePedido(@RequestBody Detalle_Pedido detallePedido) {
        return detallePedidoServicio.crearDetallePedido(detallePedido);
    }

    @PutMapping("/{id}")
    public Detalle_Pedido actualizarDetallePedido(@PathVariable int id, @RequestBody Detalle_Pedido detallePedido) {
        return detallePedidoServicio.actualizarDetallePedido(id, detallePedido);
    }

    @GetMapping("/{id}")
    public Detalle_Pedido obtenerDetallePorId(@PathVariable int id) {
        return detallePedidoServicio.obtenerDetallePorId(id);
    }

    @GetMapping("/pedidos/{id_pedido}")
    public List<Detalle_Pedido> obtenerDetallePorId_pedido(@PathVariable("id_pedido") int id_pedido) {
        return detallePedidoServicio.obtenerDetallePorId_pedido(id_pedido);
    }

    @GetMapping
    public List<Detalle_Pedido> obtenerTodosLosDetalles() {
        return detallePedidoServicio.obtenerTodosLosDetalles();
    }

    @GetMapping("/sucursal/{sucursal}")
    public List<Detalle_Pedido> obtenerTodosLosDetallesPorSucursal(@PathVariable("sucursal") String sucursal) {
        return detallePedidoServicio.obtenerTodosLosDetallesPorSucursal(sucursal);
    }

    @DeleteMapping("/{id}")
    public void eliminarDetallePedido(@PathVariable int id) {
        detallePedidoServicio.eliminarDetallePedido(id);
    }
}
