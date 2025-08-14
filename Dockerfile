# Etapa de build
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src

# Compila e empacota o projeto (gera o JAR)
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jdk-slim

WORKDIR /app

# Expõe a porta da aplicação
EXPOSE 8080

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/deploy_render-1.0.0.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
