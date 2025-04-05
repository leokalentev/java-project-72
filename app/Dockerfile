FROM gradle:8.5.0-jdk21

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src

RUN ./gradlew --no-daemon dependencies

RUN ./gradlew --no-daemon build

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar", "--port=7070"]