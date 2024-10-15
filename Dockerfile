FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/medico_api-0.0.1-SNAPSHOT.jar medico_api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "medico_api-0.0.1-SNAPSHOT.jar"]
