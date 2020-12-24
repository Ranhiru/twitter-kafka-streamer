package com.ranhiru.twitterstreamer

class Producer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val tweetsProducer = TweetsProducer()
            val keywords = listOf("bitcoin", "ethereum", "cryptocurrency")
            val streamClient = TwitterStreamClient(tweetsProducer, keywords)
            streamClient.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                tweetsProducer.shutdown()
                streamClient.shutdown()
            })
        }
    }
}
