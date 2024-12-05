FROM openjdk:17-jdk-slim as build


WORKDIR /app

COPY specialE/eureka-server-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8761

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]