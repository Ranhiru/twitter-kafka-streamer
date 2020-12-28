package com.ranhiru.twitterconsumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*

class TwitterKafkaConsumer(
    private val elasticSearchConsumer: ElasticSearchConsumer
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var consumer: KafkaConsumer<String, String>
    private var keepPolling = true

    init {
        logger.info("Booting Consumer");

        val consumerGroup = "kafka-twitter-consumer-group"
        val topic = "tweets"

        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup)

        consumer = KafkaConsumer<String, String>(properties)
        consumer.subscribe(listOf(topic))
    }

    fun run() {
        while (keepPolling) {
            val records = consumer.poll(Duration.ofMillis(1000))

            records.forEach { record ->
                logger.info(
                    """
                    Inserting value in to ElasticSearch
                    Value: ${record.value()}
                    Topic: ${record.topic()}
                    """.trimIndent()
                )
                elasticSearchConsumer.putDocument(record.value())
            }
        }
    }

    fun shutdown() {
        keepPolling = false
        consumer.unsubscribe()
        consumer.close()
    }
}
