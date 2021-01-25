package com.ranhiru.twitterstream

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig.USER_INFO_CONFIG
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import org.apache.kafka.streams.StreamsConfig

object Config {
    fun getAuthConfig(): Map<String, String> {
        val username = System.getenv("SASL_LOGIN_USERNAME")
        val password = System.getenv("SASL_LOGIN_PASSWORD")

        return mapOf(
            StreamsConfig.SECURITY_PROTOCOL_CONFIG to "SASL_SSL",
            "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required username='$username' password='$password';",
            "sasl.mechanism" to "PLAIN"
        )
    }

    fun getSchemaRegistryConfig(): Map<String, String> {
        val schemaRegistryUrl = System.getenv("SCHEMA_REGISTRY_URL")
        val basicAuthUserInfo = System.getenv("SCHEMA_REGISTRY_USER_INFO")

        return mapOf(
            SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
            USER_INFO_CONFIG to basicAuthUserInfo,
            BASIC_AUTH_CREDENTIALS_SOURCE to "USER_INFO",
            StreamsConfig.SECURITY_PROTOCOL_CONFIG to "SASL_SSL"
        )
    }

    fun getBoostrapServerConfig(): Map<String, String> {
        val bootStrapServers = System.getenv("BOOTSTRAP_SERVERS")
        return mapOf(
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to bootStrapServers
        )
    }
}