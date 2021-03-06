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
    implementation(project(":schemas"))
    implementation(kotlin("stdlib"))
    implementation("org.apache.kafka:kafka-clients:2.7.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("io.confluent", "kafka-avro-serializer", "5.3.0")
    testImplementation("junit", "junit", "4.12")
}
