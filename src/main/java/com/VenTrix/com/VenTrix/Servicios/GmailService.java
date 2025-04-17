package com.VenTrix.com.VenTrix.Servicios;

import com.VenTrix.com.VenTrix.Configuracion.GmailServiceBuilder;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;
import java.util.Base64;

@Service
public class GmailService {

    @Autowired
    private Usuario_Servicio servicio;

    private static final String APPLICATION_NAME = "VenTrix";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/app/tokens";
    private static final String USER_IDENTIFIER = "ventrix-user";
    
    // Scope corregido usando la URL completa
    private static final String GMAIL_SCOPE = "https://www.googleapis.com/auth/gmail.send";

    public void sendEmail(String to) throws Exception {
        if (!servicio.existeUsuarioPorCorreo(to)) {
            throw new Exception("Correo no registrado");
        }

        String documento = servicio.getUsuarioByCorreo(to);
        String nombre = servicio.getUsuarioByNombre(documento);

        Gmail service = getGmailService();

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("mg800487@gmail.com", "VenTrix"));
        message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Restablecimiento de contraseña");

        String cuerpoHtml = String.format("""
        <h2>Hola %s</h2>
        <p>Recibimos una solicitud para restablecer tu contraseña.</p>
        <p>Haz clic en el siguiente botón para continuar:</p>
        <a href="https://frontend-ventrix-production.up.railway.app/restablecer/%s"
           style="display:inline-block;padding:10px 20px;color:white;background-color:#007BFF;border-radius:5px;text-decoration:none;">
           Restablecer contraseña
        </a>
        <p>Si no hiciste esta solicitud, puedes ignorar este mensaje.</p>
        <br>
        <p>Gracias,<br>Equipo VenTrix</p>
        """, nombre, to);

        message.setContent(cuerpoHtml, "text/html; charset=utf-8");

        Message gmailMessage = createMessageWithEmail(message);
        service.users().messages().send("mg800487@gmail.com", gmailMessage).execute();
    }

    public Gmail getGmailService() throws IOException, GeneralSecurityException {
        return GmailServiceBuilder.buildGmailService();
    }

    public void authorizeAndStoreCredentials(String authorizationCode) throws IOException, GeneralSecurityException {
        GmailServiceBuilder.authorizeAndStoreCredentials(authorizationCode);
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
}