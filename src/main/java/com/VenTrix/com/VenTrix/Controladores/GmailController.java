package com.VenTrix.com.VenTrix.Controladores;

import com.VenTrix.com.VenTrix.Servicios.GmailService;
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/Callback")
    public ResponseEntity<String> handleGoogleCallback(@RequestParam("code") String code) {
        try {
            gmailService.authorizeAndStoreCredentials(code);
            return ResponseEntity.ok("Autorización exitosa. Tokens guardados en /app/tokens");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al autorizar: " + e.getMessage());
        }
    }
}