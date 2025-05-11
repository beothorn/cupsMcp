plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.github.beothorn"
version = "3.0.0"
extra["springAiVersion"] = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations.all {
    exclude(group = "commons-logging", module = "commons-logging")
}

repositories {
    mavenCentral()

    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
    maven {
        name = "Central Portal Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

dependencies {
    implementation("org.springframework.ai:spring-ai-starter-mcp-server")
    implementation("org.springframework:spring-web")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.cups4j:cups4j:0.7.6")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

springBoot {
    mainClass.set("com.github.beothorn.PrintServerApplication")
}