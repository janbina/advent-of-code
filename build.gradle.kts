plugins {
    kotlin("jvm") version "2.3.0"
}

group = "com.janbina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.12.4544")
}
