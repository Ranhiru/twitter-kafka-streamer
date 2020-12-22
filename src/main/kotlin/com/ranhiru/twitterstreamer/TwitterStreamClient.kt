package com.ranhiru.twitterstreamer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import twitter4j.FilterQuery
import twitter4j.StatusListener
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class TwitterStreamClient(statusListener: StatusListener, private val filterKeywords: List<String>) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var twitterStream: TwitterStream

    init {
        val configuration = twitterConfiguration()
        twitterStream = TwitterStreamFactory(configuration).instance
        twitterStream.addListener(statusListener);
    }

    fun run() {
        val fq = filterQuery()
        twitterStream.filter(fq);
    }

    fun shutdown() {
        logger.info("Shutting down twitter stream client")
        twitterStream.clearListeners()
        twitterStream.shutdown()
    }

    private fun filterQuery(): FilterQuery {
        val fq = FilterQuery()
        fq.track(*filterKeywords.toTypedArray())
        return fq
    }

    private fun twitterConfiguration(): Configuration {
        val consumerKey = System.getenv("CONSUMER_KEY")
        val consumerSecret = System.getenv("CONSUMER_SECRET")
        val accessToken = System.getenv("ACCESS_TOKEN")
        val accessTokenSecret = System.getenv("ACCESS_TOKEN_SECRET")

        val cb = ConfigurationBuilder()
        return cb.setJSONStoreEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret)
            .build()
    }
}