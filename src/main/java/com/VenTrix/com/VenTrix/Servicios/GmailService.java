package com.VenTrix.com.VenTrix.Servicios;

import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

@Service
public class GmailService {

    @Autowired
    private Usuario_Servicio servicio;

    private static final String APPLICATION_NAME = "VenTrix";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);
    private static final String TOKENS_DIRECTORY_PATH = "/app/tokens";

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
        <a href="http://localhost:8081/restablecer/%s"
           style="display:inline-block;padding:10px 20px;color:white;background-color:#007BFF;border-radius:5px;text-decoration:none;">
           Restablecer contraseña
        </a>
        <p>Si no hiciste esta solicitud, puedes ignorar este mensaje.</p>
        <br>
        <p>Gracias,<br>Equipo VenTrix</p>
        """, nombre,to);

        message.setContent(cuerpoHtml, "text/html; charset=utf-8");

        // Convierte el mensaje a formato compatible con Gmail API
        Message gmailMessage = createMessageWithEmail(message);

        // Envíalo usando la API de Gmail
        service.users().messages().send("mg800487@gmail.com", gmailMessage).execute();
    }

    private Gmail getGmailService() throws IOException, GeneralSecurityException {
        // Leer client_secret desde variable de entorno
        String secretJson = System.getenv("GOOGLE_CLIENT_SECRET");
        if (secretJson == null) {
            throw new IllegalStateException("La variable GOOGLE_CLIENT_SECRET no está definida");
        }

        InputStream in = new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Restaurar token desde variable si no existe el archivo
        Path tokensPath = Paths.get(TOKENS_DIRECTORY_PATH);
        Path credentialFile = tokensPath.resolve("StoredCredential");

        if (!Files.exists(credentialFile)) {
            String storedCredentialBase64 = System.getenv("GOOGLE_STORED_CREDENTIAL_B64");
            if (storedCredentialBase64 == null) {
                throw new IllegalStateException("La variable GOOGLE_STORED_CREDENTIAL_B64 no está definida y no existe el token en disco");
            }
            Files.createDirectories(tokensPath);
            byte[] decoded = Base64.getDecoder().decode(storedCredentialBase64);
            Files.write(credentialFile, decoded);
        }

        // Crear flujo OAuth con el token restaurado
        var flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                SCOPES
        ).setDataStoreFactory(new FileDataStoreFactory(tokensPath.toFile()))
                .setAccessType("offline")
                .build();

        // Cargar credencial
        var credential = flow.loadCredential("user");
        if (credential == null) {
            throw new IllegalStateException("No se pudo cargar la credencial desde el token almacenado");
        }

        // Construir el servicio de Gmail
        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
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