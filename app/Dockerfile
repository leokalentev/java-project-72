FROM gradle:8.5.0-jdk21

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean build --no-daemon --stacktrace

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT-all.jar", "--port=7070"]