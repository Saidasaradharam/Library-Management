# --- Stage 1: Build the Application ---
# Use Maven with Temurin JDK 17 (active and stable)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src /app/src

# Compile the code and create the JAR file
RUN mvn clean package -DskipTests

# --- Stage 2: Create the Lean Runtime Image ---
# Minimal JRE based on Eclipse Temurin
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the generated JAR file from the 'build' stage
COPY --from=build "/app/target/*.jar" app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
