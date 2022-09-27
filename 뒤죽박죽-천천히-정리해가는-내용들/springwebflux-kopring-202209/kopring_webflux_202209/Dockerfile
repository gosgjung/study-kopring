FROM eclipse-temurin:19 AS prj-builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:19
RUN mkdir /opt/app
COPY --from=prj-builder build/libs/*.jar /opt/app/kopring-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/app/kopring-app.jar"]
