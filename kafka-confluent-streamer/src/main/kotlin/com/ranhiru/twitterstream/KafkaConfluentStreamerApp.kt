package com.ranhiru.twitterstream

class KafkaConfluentStreamerApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val confluentStreamer = ConfluentStreamer()
            confluentStreamer.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                confluentStreamer.shutdown()
            })
        }
    }
}
