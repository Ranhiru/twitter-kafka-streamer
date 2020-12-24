plugins {
    java
    kotlin("jvm")
}

group = "com.ranhiru"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.twitter4j:twitter4j-stream:4.0.7")
    implementation("org.apache.kafka:kafka-clients:2.6.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    testImplementation("junit", "junit", "4.12")
}
