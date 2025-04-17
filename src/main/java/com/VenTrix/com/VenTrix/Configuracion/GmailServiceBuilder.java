package com.VenTrix.com.VenTrix.Configuracion;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GmailServiceBuilder {
    private static final String APPLICATION_NAME = "VenTrix";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "/app/tokens";
    private static final String USER_IDENTIFIER = "ventrix-user";
    private static final String GMAIL_SCOPE = "https://www.googleapis.com/auth/gmail.send";

    public static Gmail buildGmailService() throws IOException, GeneralSecurityException {
        // 1. Verificar y crear directorio de tokens
        File tokensDir = new File(TOKENS_DIRECTORY_PATH);
        if (!tokensDir.exists()) {
            if (!tokensDir.mkdirs()) {
                throw new IOException("No se pudo crear el directorio para tokens");
            }
        }

        // 2. Cargar client secrets
        String secretJson = System.getenv("GOOGLE_CLIENT_SECRET");
        if (secretJson == null) {
            throw new IllegalStateException("Variable GOOGLE_CLIENT_SECRET no configurada");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8))));

        // 3. Configurar flujo de autorización
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(GMAIL_SCOPE))
                .setDataStoreFactory(new FileDataStoreFactory(tokensDir))
                .setAccessType("offline")
                .build();

        // 4. Cargar credenciales existentes
        Credential credential = flow.loadCredential(USER_IDENTIFIER);

        if (credential == null || credential.getRefreshToken() == null) {
            throw new IllegalStateException("No hay credenciales válidas. Autoriza primero via /api/email/Callback");
        }

        // 5. Construir servicio Gmail
        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void authorizeAndStoreCredentials(String authorizationCode) throws IOException, GeneralSecurityException {
        // 1. Verificar y crear directorio de tokens
        File tokensDir = new File(TOKENS_DIRECTORY_PATH);
        if (!tokensDir.exists()) {
            if (!tokensDir.mkdirs()) {
                throw new IOException("No se pudo crear el directorio para tokens");
            }
        }

        // 2. Cargar client secrets
        String secretJson = System.getenv("GOOGLE_CLIENT_SECRET");
        if (secretJson == null) {
            throw new IllegalStateException("Variable GOOGLE_CLIENT_SECRET no configurada");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new ByteArrayInputStream(secretJson.getBytes(StandardCharsets.UTF_8))));

        // 3. Configurar flujo de autorización
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(GMAIL_SCOPE))
                .setDataStoreFactory(new FileDataStoreFactory(tokensDir))
                .setAccessType("offline")
                .build();

        // 4. URL de redirección (debe coincidir con Google Cloud Console)
        String redirectUri = "https://backend-ventrix-production.up.railway.app/api/email/Callback";

        // 5. Intercambiar código por tokens
        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode)
                .setRedirectUri(redirectUri)
                .execute();

        // 6. Almacenar credenciales
        flow.createAndStoreCredential(tokenResponse, USER_IDENTIFIER);
    }
}