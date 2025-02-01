FROM eclipse-temurin:17-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY target/*.jar /app/app.jar
WORKDIR /app

ENTRYPOINT ["java","-jar","app.jar"]