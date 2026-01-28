# --- Build stage: use Maven to compile the Spring Boot application ---
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /workspace

# Project doesn't include the Maven wrapper; copy only `pom.xml` and use the
# Maven binary provided by the base image instead of `mvnw`/.mvn.
COPY pom.xml ./

# pre-download dependencies (faster subsequent builds)
RUN mvn -B -f pom.xml -DskipTests dependency:go-offline

# copy source and build artifact
COPY src ./src
RUN mvn -B -DskipTests package


# --- Runtime stage: lightweight JRE image ---
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the fat jar produced by the builder
ARG JAR_FILE=target/*.jar
COPY --from=builder /workspace/${JAR_FILE} app.jar

# Expose default Spring Boot port (override with SPRING_BOOT_INACTIVE if needed)
EXPOSE 9000

# Run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
