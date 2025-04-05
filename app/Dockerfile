FROM gradle:8.5.0-jdk21

WORKDIR /app

COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .

RUN ./gradlew --no-daemon dependencies

COPY src src
COPY config config

RUN ./gradlew --no-daemon build

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar", "--port=7070"]