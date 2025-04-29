#FROM eclipse-temurin:21-jre-alpine
#COPY target/aomail-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

# Stage 1: Build Stage using Maven and OpenJDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package

# Stage 2: Runtime Stage using OpenJDK 21
FROM openjdk:21

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
