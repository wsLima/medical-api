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

# Expõe porta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java","-jar","app.jar"]
