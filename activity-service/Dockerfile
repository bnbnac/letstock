FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/mod2-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
