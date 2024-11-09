# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file and your project files
COPY pom.xml .
COPY src ./src

# Install Maven and build the project
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/ExpenseTracker-0.0.1-SNAPSHOT.jar"]
