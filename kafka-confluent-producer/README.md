# Confluent Kafka Producer

This is an example Kafka producer that connects to a remote Kafka cluster (Confluent) and publishes payment events.

Additionally, this project uses an Apache avro schema (schemas/src/main/avro/Payment.avsc) along with Confluent schema registry.

#### Requirements

* Confluent Cloud account and cluster (https://confluent.cloud/signup)
* New topic named `payments`
* New API access credentials (Key and Secret) for Confluent Cloud - https://docs.confluent.io/cloud/current/client-apps/api-keys.html
* Setup Read & Write ACL permissions to the `payments` topic to the generated API access credentials
* Setup Schema Registry API Access - https://docs.confluent.io/cloud/current/client-apps/api-keys.html

#### How to Run

* Add the following ENV variables to the launch configuration (`com.ranhiru.twitterstream.KafkaConfluentProducerApp`)
    * `BOOTSTRAP_SERVERS`  - This is the bootstrap server for the given cluster
    * `SASL_LOGIN_USERNAME` - This is the *key* part of the API access credentials
    * `SASL_LOGIN_PASSWORD` - This is the *secret* part of the API access credentials
    * `SCHEMA_REGISTRY_URL` - This is the schema registry URL provided by Confluent Cloud
    * `SCHEMA_REGISTRY_USER_INFO` - This is the schema registry API key and secret in the following format - `key:secret`

       E.g _schema-registry-access-key:schema-registry-access-secret_

    * Run the `com.ranhiru.twitterstream.KafkaConfluentProducerApp` and provide a payment id and a payment amount to produce a record
