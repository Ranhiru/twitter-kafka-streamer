plugins {
    java
    kotlin("jvm")
    id("com.commercehub.gradle.plugin.avro") version("0.22.0")
}

group = "com.ranhiru"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.avro:avro:1.10.1")
    implementation("org.apache.kafka:kafka-clients:2.6.0")
    implementation("org.apache.kafka:kafka-streams:2.7.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("io.confluent", "kafka-avro-serializer", "5.3.0")
    implementation("org.apache.avro", "avro", "1.10.1")
    testImplementation("junit", "junit", "4.12")
}
