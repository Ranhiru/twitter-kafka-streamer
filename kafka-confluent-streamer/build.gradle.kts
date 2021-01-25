plugins {
    java
    kotlin("jvm")
}

group = "com.ranhiru"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":schemas"))
    implementation("org.apache.kafka:kafka-streams:6.0.1-ccs")
    implementation("org.apache.kafka:kafka-clients:6.0.1-ccs")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("io.confluent", "kafka-streams-avro-serde", "6.0.1")
    testImplementation("junit", "junit", "4.12")
}
