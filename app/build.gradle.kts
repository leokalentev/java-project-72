plugins {
    id("java")
    id("org.sonarqube") version "6.0.1.5171"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", "leokalentev_java-project-72")
        property("sonar.organization", "leokalentev")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}