package com.ranhiru.twitterstream

class Stream {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val twitterKafkaStream = TwitterKafkaStream()
            twitterKafkaStream.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                twitterKafkaStream.shutdown()
            })
        }
    }
}
