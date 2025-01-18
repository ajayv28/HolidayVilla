FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

# Copy source code and POM
COPY ./src src/
COPY ./pom.xml pom.xml

# Build the application
RUN mvn clean install -DskipTests

# Runtime image
FROM eclipse-temurin:17-jre-alpine

# Copy the built application JAR
COPY --from=builder target/app.jar app.jar

# Health check for runtime
HEALTHCHECK CMD curl --fail http://localhost:8080/health || exit 1

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
