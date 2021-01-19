package com.ranhiru.twitterstream

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class TwitterKafkaStream {
    lateinit var kafkaStreams: KafkaStreams
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun run() {

        // Create properties
        val properties = Properties()
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-twitter-stream")
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde::class.java.name)

        // Create topology
        val streamsBuilder =  StreamsBuilder()
        val twitterKafkaStream = streamsBuilder.stream<String, String>("tweets")

        twitterKafkaStream.filter { _, jsonTweet ->
            val jsonObject = JSONObject(jsonTweet)
            val numOfFollowers = jsonObject.optQuery("/user/followers_count") as Int
            logger.info("Number of followers for tweet is $numOfFollowers")
            numOfFollowers > 10000
        }.to("important_tweets")

        // Build the topology
        kafkaStreams = KafkaStreams(streamsBuilder.build(), properties)


        // Start the streams application
        kafkaStreams.start()
    }

    fun shutdown() {
        kafkaStreams.close()
        kafkaStreams.cleanUp()
    }
}