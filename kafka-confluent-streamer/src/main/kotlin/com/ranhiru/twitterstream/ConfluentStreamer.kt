package com.ranhiru.twitterstream

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class ConfluentStreamer {
    lateinit var kafkaStreams: KafkaStreams

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun run() {
        val properties = getConfluentProperties()

        // Create topology
        val streamsBuilder =  StreamsBuilder()
        val topic = "payments"
        val paymentsStream = streamsBuilder.stream<String, Payment>(topic)

        paymentsStream
            .peek { key, value -> logger.info("Received value $key - $value") }

        // Build the topology
        val topology = streamsBuilder.build()
        kafkaStreams = KafkaStreams(topology, properties)
        kafkaStreams.start()
    }

    fun shutdown() {
        kafkaStreams.close()
    }

    private fun getConfluentProperties(): Properties {
        val properties = Properties()

        // StreamsConfig Config
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "confluent-payment-stream")
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java.name)
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde::class.java.name)
        properties.setProperty(StreamsConfig.REPLICATION_FACTOR_CONFIG, "3")

        properties.putAll(Config.getBoostrapServerConfig())

        // Authentication Config
        properties.putAll(Config.getAuthConfig())

        // Schema Registry Config
        properties.putAll(Config.getSchemaRegistryConfig())

        return properties
    }
}