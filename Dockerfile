# Build stage
FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

# Set the working directory
WORKDIR /app

# Copy source code and POM
COPY ./src /app/src/
COPY ./pom.xml /app/pom.xml

# Build the application
RUN mvn clean install -DskipTests

# Runtime image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built application JAR from the builder stage
COPY --from=builder /app/target/HolidayVilla-0.0.1-SNAPSHOT.jar app.jar

# Health check for runtime
HEALTHCHECK CMD curl --fail http://localhost:8080/health || exit 1

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
