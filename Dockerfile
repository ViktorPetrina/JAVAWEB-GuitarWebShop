FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

VOLUME /tmp

COPY target/GuitarWebShop-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
