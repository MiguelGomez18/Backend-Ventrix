package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Entidades.Restaurante;
import com.VenTrix.com.VenTrix.Servicios.GmailService;
import com.VenTrix.com.VenTrix.Servicios.Usuario_Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class GmailController {

    @Autowired
    private GmailService gmailService;

    @PostMapping("/send")
    public ResponseEntity<String> enviarCorreo(@RequestBody Map<String, String> request) {
        String to = request.get("to");

        try {
            gmailService.sendEmail(to);
            return ResponseEntity.ok("Correo enviado con éxito!");
        } catch (Exception e) {
            // Esto lanza el error correcto al frontend (como 404, etc.)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
