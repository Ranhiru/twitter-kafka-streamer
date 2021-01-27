# kafka-streams

There are two example apps in the `kafka-streams` module.

## `ImportantTweetsApp`

Kafka Streams app that reads tweets from the `tweets` topic, identifies
tweets that were tweeted by users that has more than 10,000 followers and puts them to a new topic `important_tweets`

#### Requirements
* A topic named `tweets` created in your local Kafka broker.
* A topic named `important_tweets` created in your local Kafka broker.
* Either the `kafka-twitter-producer` or `kafka-connect` app running to stream tweets to `tweets` topic

#### How to Run
* Run the Main Class: `com.ranhiru.twitterstream.ImportantTweetsApp`

## `WordsCountApp`

Kafka Streams app that reads messages from a topic named `streams-plaintext-input` and writes the frequency of each word to a new stream named
`streams-wordcount-output`. This app connects to Confluent cloud.

#### Requirements
* A topic named `streams-plaintext-input` created in Confluent cloud
* A topic named `streams-wordcount-output` created in Confluent cloud
* Created a new Confluent Cloud account and cluster (https://confluent.cloud/signup)
* Generate new API access credentials (Key and Secret) for Confluent Cloud - https://docs.confluent.io/cloud/current/client-apps/api-keys.html
* Setup READ & WRITE ACL permissions to the `streams-plaintext-input` and `streams-wordcount-output` topics
* Setup CREATE ACL permission to `kafka-words-stream` topic (prefixed)
* Setup READ ACL permissions to the `kafka-words-stream` consumer group
* Add the following ENV variables to the launch configuration (`com.ranhiru.twitterstream.KafkaConfluentProducerApp`)
    * `BOOTSTRAP_SERVERS`  - This is the bootstrap server for the given cluster
    * `SASL_LOGIN_USERNAME` - This is the *key* part of the API access credentials
    * `SASL_LOGIN_PASSWORD` - This is the *secret* part of the API access credentials
* A kafka-console-producer to add new messages to the `streams-plaintext-input` code

#### How to Run
* Run the Main Class: `com.ranhiru.twitterstream.WordsCountApp`
