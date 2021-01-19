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
    implementation("org.apache.kafka:kafka-streams:2.7.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:7.10.1")
    implementation("org.json:json:20201115")

    testImplementation("junit", "junit", "4.12")
}
