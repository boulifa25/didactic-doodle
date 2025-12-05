FROM eclipse-temurin:17-jdk-alpine

# Copier le bon JAR (nom exact du fichier généré)
COPY target/student-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app.jar"]
