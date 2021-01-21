package com.ranhiru.words

class WordsCountApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val wordsCountKafkaStream = WordsCountKafkaStream()
            wordsCountKafkaStream.run()

            Runtime.getRuntime().addShutdownHook(Thread {
                wordsCountKafkaStream.shutdown()
            })
        }
    }
}
