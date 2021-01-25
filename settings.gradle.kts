rootProject.name = "twitter-streamer"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven {
            name = "JCenter Gradle Plugins"
            url  = uri("https://dl.bintray.com/gradle/gradle-plugins")
        }
        maven {
            name = "Confluent"
            url = uri("https://packages.confluent.io/maven/")
        }
    }
}

include("kafka-twitter-producer")
include("kafka-twitter-consumer")
include("kafka-streams")
include("kafka-confluent-producer")
include("kafka-confluent-streamer")
include("schemas")
