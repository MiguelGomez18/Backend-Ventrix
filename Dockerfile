# Etapa de construcción: Maven + Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY pom.xml .
COPY src/ src/

# Compila el proyecto sin ejecutar los tests
RUN mvn clean package -DskipTests

# Etapa de ejecución: Java 21
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia el JAR compilado desde la etapa de construcción
COPY --from=builder /app/target/VenTrix-0.0.1-SNAPSHOT.jar app.jar

# Crea directorio para tokens (si lo necesitas)
RUN mkdir -p /app/tokens /app/imagenes

COPY imagenes /app/imagenes

# Expone el puerto que usará tu aplicación
EXPOSE 8890

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java -DGOOGLE_CLIENT_SECRET=\"$GOOGLE_CLIENT_SECRET\" -jar app.jar"]
