# Step 1: Build Stage
FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

# Set the working directory for the build process
WORKDIR /app

# Copy the necessary files
COPY ./pom.xml pom.xml
COPY ./src src/

# Run Maven build with debug information to help identify issues
RUN mvn clean package -DskipTests -X

# Step 2: Final Image (Runtime Stage)
FROM eclipse-temurin:17-jre-alpine

# Set the working directory for the runtime container
WORKDIR /app

# Copy the jar file from the builder image to the runtime image
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
