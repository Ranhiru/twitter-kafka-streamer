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

Kafka Streams app that reads messages from a topic named `streams-plaintext` and writes the frequency of each word to a new stream named
`streams-wordcount-output`

#### Requirements
* A topic named `streams-plaintext-input` created in your local Kafka broker.
* A topic named `streams-wordcount-output` created in your local Kafka broker.
* A kafka-console-producer to add new messages to the `streams-plaintext-input` code

#### How to Run
* Run the Main Class: `com.ranhiru.twitterstream.WordsCountApp`
