package com.ranhiru.twitterstream

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.CommonClientConfigs.SECURITY_PROTOCOL_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig

object Config {
    fun getAuthConfig(): Map<String, String> {
        val username = System.getenv("SASL_LOGIN_USERNAME")
        val password = System.getenv("SASL_LOGIN_PASSWORD")

        return mapOf(
            SECURITY_PROTOCOL_CONFIG to "SASL_SSL",
            "sasl.jaas.config" to "org.apache.kafka.common.security.plain.PlainLoginModule required username='$username' password='$password';",
            "sasl.mechanism" to "PLAIN"
        )
    }

    fun getSchemaRegistryConfig(): Map<String, String> {
        val schemaRegistryUrl = System.getenv("SCHEMA_REGISTRY_URL")
        val basicAuthUserInfo = System.getenv("SCHEMA_REGISTRY_USER_INFO")

        return mapOf(
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG to schemaRegistryUrl,
            SchemaRegistryClientConfig.USER_INFO_CONFIG to basicAuthUserInfo,
            SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE to "USER_INFO",
            SECURITY_PROTOCOL_CONFIG to "SASL_SSL"
        )
    }

    fun getBoostrapServerConfig(): Map<String, String> {
        val bootStrapServers = System.getenv("BOOTSTRAP_SERVERS")
        return mapOf(
            BOOTSTRAP_SERVERS_CONFIG to bootStrapServers
        )
    }

    fun getProducerConfig(): Map<String, String> {
        return mapOf(
            ProducerConfig.ACKS_CONFIG to "all",
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to "true",
            ProducerConfig.COMPRESSION_TYPE_CONFIG to "snappy",
            ProducerConfig.LINGER_MS_CONFIG to "20",
            ProducerConfig.BATCH_SIZE_CONFIG to "32768"
        )
    }
}