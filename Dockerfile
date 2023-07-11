# Use base Gradle image
FROM gradle:7.6.2-jdk17 AS build

# Set project deployment directory
WORKDIR /vacations-api

# Copy build.gradle and settings.gradle files to the working directory
COPY build.gradle .
COPY settings.gradle .

# Copy the entire project
COPY . .

# Build the application
RUN gradle clean build

# Use a lightweight Java image
FROM eclipse-temurin:17.0.7_7-jre-alpine

# Set the working directory for the runtime container
WORKDIR /vacations-api

# Copy the built JAR file from the build stage
COPY --from=build /vacations-api/build/libs/vacations-api.jar .

EXPOSE 5002

# Run the Spring Boot application
CMD ["java", "-jar", "vacations-api.jar"]
