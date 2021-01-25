plugins {
    java
    kotlin("jvm")
    id("com.commercehub.gradle.plugin.avro") version("0.22.0")
}

group = "com.ranhiru"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.avro:avro:1.10.1")
    testImplementation("junit", "junit", "4.12")
}
