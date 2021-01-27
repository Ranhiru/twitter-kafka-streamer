# kafka-twitter-producer

This is a simple Kafka Producer that reads tweets with specified keywords using Twitter Streaming API and writes these
records to a topic called `tweets`.

#### Requirements
* A topic named `tweets` created in your local Kafka broker.
* Twitter API Developer Account (https://developer.twitter.com/en/apply-for-access)
  After Twitter API Developer account is created, generate consumer keys and access tokens from the Developer Portal.

#### How to Run
* Set the following ENV variables for the launch configuration from the above generated Twitter API keys and
access tokens
  `CONSUMER_KEY`, `CONSUMER_SECRET`, `ACCESS_TOKEN`, `ACCESS_TOKEN_SECRET`
* Run the Main Class: `com.ranhiru.twitterstreamer.Producer`