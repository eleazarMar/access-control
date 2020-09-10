import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("org.jlleitschuh.gradle.ktlint") version("9.4.0")
}

group = "eehackspace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/kotlin/ktor")
    }
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlinx")
    }
}
dependencies {
    testImplementation(kotlin("test-junit5"))

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("io.github.config4k:config4k:0.4.2")
    implementation("io.github.microutils:kotlin-logging:1.8.3")
    implementation("io.ktor:ktor-gson:1.4.0")
    implementation("io.ktor:ktor-server-netty:1.4.0")
    implementation("mysql:mysql-connector-java:8.0.21")
    implementation("org.flywaydb:flyway-core:6.5.5")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}
application {
    mainClassName = "ServerKt"
}
