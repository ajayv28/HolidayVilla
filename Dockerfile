# Stage 1: Build the JAR
FROM maven:3.9.4-eclipse-temurin-17 AS builder
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/HolidayVilla-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]