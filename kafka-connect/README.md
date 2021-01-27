# kafka-connect

This project uses a source connector (https://github.com/jcustenborder/kafka-connect-twitter) which is can be used as a replacement
`kafka-twitter-producer`. The advantage with this source connector is that we do not have to write any code.

#### Requirements
* A topic named `twitter_status_connect` created in your local Kafka broker (Configurable in `twitter.properties` file)
* A topic named `twitter_delete_connect` created in your local Kafka broker (Configurable in `twitter.properties` file).
* Twitter API Developer Account (https://developer.twitter.com/en/apply-for-access)
  After Twitter API Developer account is created, generate consumer keys and access tokens from the Developer Portal.

#### How to Run
* Update the `twitter.properties` file and replace `CONSUMER_KEY`, `CONSUMER_SECRET`, `ACCESS_TOKEN`, `ACCESS_TOKEN_SECRET` with
the generated access credentials.
* `$ connect-standalone connect-standalone.properties twitter.properties`
