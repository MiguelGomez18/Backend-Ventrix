package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Tipo_pago;
import com.VenTrix.com.VenTrix.Servicios.Tipo_pago_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo_pago")
public class Tipo_pago_Controlador {


    @Autowired
    private Tipo_pago_Servicio tipoPagoServicio;

    // Registrar un nuevo tipo de pago
    @PostMapping("/registrar")
    public ResponseEntity<Tipo_pago> registrarTipoPago(@RequestBody Tipo_pago tipoPago) {
        Tipo_pago saveTipoPago = tipoPagoServicio.addTipoPago(tipoPago);
        return new ResponseEntity<>(saveTipoPago, HttpStatus.CREATED);
    }

    // Actualizar un tipo de pago por su ID
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTipoPago(@PathVariable int id, @RequestBody Tipo_pago tipoPago) {
        Tipo_pago updateTipoPago = tipoPagoServicio.updateTipoPago(id, tipoPago);
        return new ResponseEntity<>("Tipo de pago actualizado correctamente", HttpStatus.OK);
    }


    // Obtener todos los tipos de pago
    @GetMapping
    public ResponseEntity<List<Tipo_pago>> obtenerTiposPago() {
        List<Tipo_pago> tiposPago = tipoPagoServicio.getAllTipoPagos();
        return new ResponseEntity<>(tiposPago, HttpStatus.OK);
    }

    @GetMapping("/id_sucursal/{id_sucursal}")
    public ResponseEntity<List<Tipo_pago>> obtenerTiposPagoSucursal(@PathVariable("id_sucursal") String id_sucursal) {
        List<Tipo_pago> tiposPago = tipoPagoServicio.getAllTipoPagosSucursal(id_sucursal);
        return new ResponseEntity<>(tiposPago, HttpStatus.OK);
    }

    // Obtener un tipo de pago por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Tipo_pago> obtenerTipoPago(@PathVariable int id) {
        Tipo_pago tipoPago = tipoPagoServicio.getTipoPagoById(id);
        return new ResponseEntity<>(tipoPago, HttpStatus.OK);
    }

    // Eliminar un tipo de pago por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipoPago(@PathVariable int id) {
        tipoPagoServicio.deleteTipoPago(id);
        return new ResponseEntity<>("Tipo de pago eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
