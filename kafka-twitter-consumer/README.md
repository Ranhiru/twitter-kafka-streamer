# kafka-twitter-consumer

This is a Kafka Consumer that reads tweets generated by the `kafka-twitter-producer` from the `tweets` kafka topic
and writes them to an elasticsearch index named `twitter`

#### Requirements
* Local elastic-search installation running on port `9200`
* The `kafka-twitter-producer` to have been run or currently running to write tweets to the `tweets` kafka topic

#### How to Run
* Run the Main Class: `com.ranhiru.twitterstreamer.Consumer`