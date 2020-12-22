package com.ranhiru.twitterstreamer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import twitter4j.*
import java.util.*

class TweetsProducer: StatusListener {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val producer: KafkaProducer<String, String>;

    init {
        logger.info("Starting App!")

        val properties = getKafkaProperties()
        producer = KafkaProducer<String, String>(properties)
    }

    private fun getKafkaProperties(): Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy")
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20")
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "32768")
        return properties
    }

    private fun consumeTweet(status: Status) {
        logger.info("Received tweet - ${status.id}")
        val json = TwitterObjectFactory.getRawJSON(status)

        val record = ProducerRecord<String, String>(TOPIC_NAME, json) //
        producer.send(record)
    }

    fun shutdown() {
        logger.info("Shutting down tweets Kafka producer")
        producer.flush()
        producer.close()
    }

    override fun onException(ex: Exception) {

    }

    override fun onStatus(status: Status) {
        consumeTweet(status)
    }

    override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {

    }

    override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
    }

    override fun onScrubGeo(userId: Long, upToStatusId: Long) {
    }

    override fun onStallWarning(warning: StallWarning) {
    }

    companion object {
        private const val TOPIC_NAME = "cryptocurrency_tweets"
    }
}