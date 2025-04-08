FROM eclipse-temurin:21-jdk-alpine

# Set working directory (cleaner file structure inside the container)
WORKDIR /app

# Add volume for temporary files (Spring Boot uses this for Tomcat)
VOLUME /tmp

COPY target/GuitarWebShop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
