FROM amazoncorretto:21-alpine
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
RUN ./gradlew build --no-daemon
COPY build/libs/spribe-task-0.0.1-SNAPSHOT.jar /app/spribe-task-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "spribe-task-0.0.1-SNAPSHOT.jar"]