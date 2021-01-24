package com.ranhiru.words

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.Produced
import org.rocksdb.Env
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


class WordsCountKafkaStream {
    lateinit var kafkaStreams: KafkaStreams
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun run() {
        // Create properties
        val properties = getConfluentProperties()

        // Create topology
        val streamsBuilder =  StreamsBuilder()
        val topic = "streams-plaintext-input"
        val wordsKafkaStream = streamsBuilder.stream<String, String>(topic)

        wordsKafkaStream
            .flatMapValues { value -> value.toLowerCase().split(" ") }
            .peek { _, value -> logger.info("Received value $value") }
            .groupBy { _, value -> value }
            .count()
            .toStream()
            .peek { key, value ->
                logger.info("Grouped $key $value")
            }
            .to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()))

        val topology = streamsBuilder.build()
        // Build the topology
        kafkaStreams = KafkaStreams(topology, properties)

        logger.info(topology.describe().toString());

        // Start the streams application
        kafkaStreams.start()
    }

    private fun getProperties(): Properties {
        val properties = Properties()
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-words-stream")
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)
        return properties
    }

    private fun getConfluentProperties(): Properties {
        val properties = Properties()

        val username = System.getenv("SASL_LOGIN_USERNAME")
        val password = System.getenv("SASL_LOGIN_PASSWORD")
        val bootStrapServers = System.getenv("BOOTSTRAP_SERVERS")

        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers)
        properties.setProperty(StreamsConfig.SECURITY_PROTOCOL_CONFIG, "SASL_SSL")
        properties.setProperty(StreamsConfig.REPLICATION_FACTOR_CONFIG, "3")
        properties.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='$username' password='$password';")
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-words-stream")
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)
        properties.setProperty("sasl.mechanism", "PLAIN")
        properties.setProperty("client.dns.lookup", "use_all_dns_ips")
        return properties
    }

    fun shutdown() {
        kafkaStreams.close()
        kafkaStreams.cleanUp()
    }
}
