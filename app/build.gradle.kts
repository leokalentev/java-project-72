plugins {
    id("org.sonarqube") version "6.0.1.5171"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    checkstyle
    id("jacoco")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.javalin:javalin:6.1.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("gg.jte:jte:3.1.9")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("gg.jte:jte:3.1.9")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
    testImplementation("io.javalin:javalin-testtools:6.1.3")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("com.konghq:unirest-java:3.14.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    implementation ("org.jsoup:jsoup:1.19.1")
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

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
}

tasks.shadowJar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/jte")
        }
    }
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

checkstyle {
    toolVersion = "10.12.4"
}






