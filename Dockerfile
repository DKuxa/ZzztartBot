# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17 as builder
LABEL authors="dimakuxa"

# Copy your source code and build the application
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Use a lighter Java runtime for running the app
FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /home/app/target/*.jar /app/app.jar

# Command to run your application
CMD ["java", "-jar", "app.jar"]