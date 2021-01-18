package com.ranhiru.twitterconsumer

import org.apache.http.HttpHost
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import org.json.JSONObject
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

    fun putJSONBulk(jsonDocuments: List<String>) {
        val bulkRequest = BulkRequest()
        jsonDocuments.forEach { document ->
            bulkRequest.add(createIndexRequest(document))
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT)
    }

    private fun createIndexRequest(json: String): IndexRequest {
        val jsonObject = JSONObject(json)
        val tweetId = jsonObject.getString("id_str").toString()

        return IndexRequest(indexName)
            .source(json, XContentType.JSON)
            .id(tweetId)
    }

    fun shutdown() {
        logger.info("Shutting down elastic search consumer")
    }
}
