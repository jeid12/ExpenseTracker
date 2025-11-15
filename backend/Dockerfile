# Use an official OpenJDK runtime as a parent image

# Build stage: compile and package the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests package

# Dev stage: useful for development runs (run with --target dev)
FROM maven:3.9.4-eclipse-temurin-17 AS dev
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
EXPOSE 8080
CMD ["mvn", "-Dspring-boot.run.profiles=dev", "spring-boot:run"]

# Final stage: small runtime image for production
FROM eclipse-temurin:17-jre-alpine AS final
WORKDIR /app
COPY --from=builder /workspace/target/ExpenseTracker-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Usage:
# - Production build (default target):
#     docker build -t expense-backend:latest ./backend
# - Development image (runs mvn spring-boot:run):
#     docker build --target dev -t expense-backend:dev ./backend
#   For local dev you may run the dev image with a bind mount to update sources live:
#     docker run --rm -p 8080:8080 -v $(pwd)/backend/src:/workspace/src -v $(pwd)/backend/pom.xml:/workspace/pom.xml expense-backend:dev
