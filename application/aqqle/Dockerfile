FROM eclipse-temurin:17-jre

WORKDIR /app

COPY api/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


