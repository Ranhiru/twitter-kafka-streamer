package com.ranhiru.twitterstream

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig.USER_INFO_CONFIG
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class ConfluentProducer {
    private val producer: KafkaProducer<String, Payment>

    init {
        val properties = getConfluentProperties()
        producer = KafkaProducer<String, Payment>(properties)
    }

    fun run()  {
        val payment = Payment.newBuilder().setAmount(100.00)
            .setId("1")
            .build()
        val producerRecord = ProducerRecord<String, Payment>("payments", payment)
        producer.send(producerRecord)
    }

    fun shutdown() {
        producer.close()
    }

    private fun getConfluentProperties(): Properties {
        val properties = Properties()

        // Producer Config
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java.name)
        properties.putAll(Config.getProducerConfig())

        // Schema Registry Config
        properties.putAll(Config.getBoostrapServerConfig())

        // Authentication Config
        properties.putAll(Config.getAuthConfig())

        // Schema Registry Config
        properties.putAll(Config.getSchemaRegistryConfig())

        return properties
    }
}