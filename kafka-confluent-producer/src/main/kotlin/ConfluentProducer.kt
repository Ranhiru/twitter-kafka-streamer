package com.ranhiru.twitterstream

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class ConfluentProducer {
    private val producer: KafkaProducer<String, Payment>
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

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