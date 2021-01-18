package com.ranhiru.twitterconsumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*

inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

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
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5000")
        properties.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "10000000")

        consumer = KafkaConsumer<String, String>(properties)
        consumer.subscribe(listOf(topic))
    }

    fun run() {
        while (keepPolling) {
            val records = consumer.poll(Duration.ofMillis(1000))
            logger.info("Received ${records.count()} records")

            if (records.count() > 0) {
                val executionTime = measureTimeMillis {
                    val jsonRecords = records.map { record -> record.value() }
                    elasticSearchConsumer.putJSONBulk(jsonRecords)

                    consumer.commitSync()
                }
                logger.info("Took $executionTime to consume records")
            } else {
                logger.info("No records to consume")
            }
        }
    }

    fun shutdown() {
        keepPolling = false
        consumer.unsubscribe()
        consumer.close()
    }
}
