package com.VenTrix.com.VenTrix.Configuracion;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class GmailServiceBuilder {
    private static final String APPLICATION_NAME = "VenTrix";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = List.of(GmailScopes.GMAIL_SEND);
    private static final String TOKENS_DIRECTORY_PATH = "/app/tokens";
    private static final String STORED_CREDENTIAL_FILENAME = "StoredCredential";

    public static Gmail getGmailService() throws Exception {
        String secretJson = System.getenv("GOOGLE_CLIENT_SECRET");
        System.out.println(secretJson);
        if (secretJson == null) {
            throw new IllegalStateException("La variable GOOGLE_CLIENT_SECRET no está definida");
        }
        
        InputStream in = new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        Path tokensPath = Paths.get(TOKENS_DIRECTORY_PATH);
        Path credentialFile = tokensPath.resolve(STORED_CREDENTIAL_FILENAME);

        if (!Files.exists(credentialFile)) {
            String storedCredentialBase64 = System.getenv("GOOGLE_STORED_CREDENTIAL_B64");
            if (storedCredentialBase64 == null) {
                throw new IllegalStateException("La variable GOOGLE_STORED_CREDENTIAL_B64 no está definida y no existe el token en disco");
            }
            Files.createDirectories(tokensPath);
            byte[] decoded = Base64.getDecoder().decode(storedCredentialBase64);
            Files.write(credentialFile, decoded);
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(tokensPath.toFile()))
                .setAccessType("offline")
                .build();

        Credential credential = flow.loadCredential("mg800487@gmail.com");

        if (credential == null) {
            throw new IllegalStateException("No se pudo cargar el token para el usuario");
        }

        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
