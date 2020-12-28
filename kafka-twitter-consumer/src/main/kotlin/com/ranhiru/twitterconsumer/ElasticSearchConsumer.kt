package com.ranhiru.twitterconsumer

import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ElasticSearchConsumer {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private val hostName = "127.0.0.1"
    private val indexName = "twitter"
    private val clientBuilder: RestClientBuilder = RestClient.builder(HttpHost(hostName, 9200, "http"))
    private val client = RestHighLevelClient(clientBuilder)

    init {
        println("Booting Twitter Elasticsearch Consumer")
    }

    fun putDocument(json: String) {
        logger.info("Inserting document to Elasticsearch")
        val indexRequest = IndexRequest(indexName)
            .source(json, XContentType.JSON)
        client.index(indexRequest, RequestOptions.DEFAULT)
    }

    fun shutdown() {
        logger.info("Shutting down elastic search consumer")
    }
}
