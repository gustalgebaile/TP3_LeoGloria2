# Build stage
FROM maven:3.9.1-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/devcalc-api-1.0-SNAPSHOT.jar devcalc.jar
EXPOSE 7000
CMD ["java", "-jar", "devcalc.jar"]