package com.ranhiru.twitterconsumer

class Consumer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val elasticSearchConsumer = ElasticSearchConsumer()
            val kafkaConsumer = TwitterKafkaConsumer(elasticSearchConsumer)
            kafkaConsumer.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                elasticSearchConsumer.shutdown()
                kafkaConsumer.shutdown()
            })
        }
    }
}
