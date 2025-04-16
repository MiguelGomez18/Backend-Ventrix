# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa final
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos el .jar generado
COPY --from=builder /app/target/*.jar app.jar

# Solo la carpeta de tokens (para uso local)
RUN mkdir -p /app/tokens

EXPOSE 8890

ENTRYPOINT ["java", "-jar", "app.jar"]
