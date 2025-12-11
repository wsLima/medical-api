# Etapa 1 — Build
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copia pom.xml e Maven wrapper
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Dá permissão ao mvnw
RUN chmod +x mvnw

# Baixa dependências (cache melhor)
RUN ./mvnw dependency:go-offline

# Copia código-fonte
COPY src ./src

# Build do jar
RUN ./mvnw clean package -DskipTests

# Etapa 2 — Runtime
FROM eclipse-temurin:21-jdk-alpine AS runtime

WORKDIR /app

# Copia jar da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Copia keystore (opcional se já estiver montando via volume)
# COPY ./ssl/api-keystore.p12 ./api-keystore.p12

# Expõe porta HTTPS
EXPOSE 8443

# Comando de inicialização com SSL
ENTRYPOINT ["sh", "-c", "java -jar app.jar \
  --server.port=8443 \
  --server.ssl.key-store=/app/api-keystore.p12 \
  --server.ssl.key-store-password=${KEYSTORE_PASSWORD} \
  --server.ssl.key-store-type=PKCS12 \
  --server.ssl.key-alias=api"]
