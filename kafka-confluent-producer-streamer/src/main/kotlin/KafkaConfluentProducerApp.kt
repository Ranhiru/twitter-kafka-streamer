package com.ranhiru.twitterstream

class KafkaConfluentProducerApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val confluentProducer = ConfluentProducer()
            confluentProducer.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                confluentProducer.shutdown()
            })
        }
    }
}
