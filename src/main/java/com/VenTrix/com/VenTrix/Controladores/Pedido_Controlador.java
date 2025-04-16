package com.VenTrix.com.VenTrix.Controladores;

import java.io.IOException;
import java.util.List;

import com.VenTrix.com.VenTrix.Entidades.Estado_Pedido;
import com.VenTrix.com.VenTrix.Entidades.Pedido;
import com.VenTrix.com.VenTrix.Entidades.Tipo_pago;
import com.VenTrix.com.VenTrix.Servicios.Pedido_Servicio;
import com.VenTrix.com.VenTrix.Servicios.Tipo_pago_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class Pedido_Controlador {

    @Autowired
    private Pedido_Servicio pedidoServicio;

    @Autowired
    private Tipo_pago_Servicio tipoPagoServicio;

    @PostMapping
    public int crearPedido(@RequestBody Pedido pedido) {
        int id = pedidoServicio.crearPedido(pedido);
        return id;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable int id, @RequestBody Map<String, Object> pedidoDatos) {

        Pedido pedidoExistente = pedidoServicio.obtenerPedidoPorId(id);

        if (pedidoDatos.containsKey("total_pedido")) {
            pedidoExistente.setTotal_pedido(Float.parseFloat(pedidoDatos.get("total_pedido").toString()));
        }
        if (pedidoDatos.containsKey("estado")) {
            pedidoExistente.setEstado(Estado_Pedido.valueOf(pedidoDatos.get("estado").toString()));
        }

        if (pedidoDatos.containsKey("tipo_pago")) {
            Map<String, Object> tipoPagoDatos = (Map<String, Object>) pedidoDatos.get("tipo_pago");
            if (tipoPagoDatos != null && tipoPagoDatos.containsKey("id")) {
                Tipo_pago tipoPago = tipoPagoServicio.getTipoPagoById(Integer.parseInt(tipoPagoDatos.get("id").toString()));
                pedidoExistente.setTipo_pago(tipoPago);
            }
        }

        Pedido pedidoActualizado = pedidoServicio.actualizarPedido(id, pedidoExistente);

        return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable int id) {
        return pedidoServicio.obtenerPedidoPorId(id);
    }

    @GetMapping
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoServicio.obtenerTodosLosPedidos();
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable int id) {
        pedidoServicio.eliminarPedido(id);
    }

}
