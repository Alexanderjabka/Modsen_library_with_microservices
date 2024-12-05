FROM openjdk:17-jdk-slim as build


WORKDIR /app

COPY specialA/api-gateway-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]