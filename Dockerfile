FROM maven:3.9.2-eclipse-temurin-21-alpine as builder

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder target/*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]

