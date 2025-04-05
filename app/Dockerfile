FROM gradle:8.5.0-jdk21

WORKDIR /app

COPY app/ /app/

RUN gradle clean build

CMD ["java", "-jar", "build/libs/app-1.0-SNAPSHOT.jar"]