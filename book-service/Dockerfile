FROM openjdk:17-jdk-slim as build


WORKDIR /app

COPY specialB/book-service-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8082

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app/app.jar"]