# Stage 1: Build the application
FROM gradle:8.1.0-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
