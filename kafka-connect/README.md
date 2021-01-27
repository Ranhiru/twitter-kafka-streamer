# kafka-connect

This project uses a source connector (https://github.com/jcustenborder/kafka-connect-twitter) which is can be used as a replacement
`kafka-twitter-producer`. The advantage with this source connector is that we do not have to write any code.

#### Requirements
* A topic named `twitter_status_connect` created in your local Kafka broker (Configurable in `twitter.properties` file)
* A topic named `twitter_delete_connect` created in your local Kafka broker (Configurable in `twitter.properties` file).
* Twitter API Developer Account (https://developer.twitter.com/en/apply-for-access)
  After Twitter API Developer account is created, generate consumer keys and access tokens from the Developer Portal.

#### How to Run
* Download `kafka-connect-twitter-$version.tar.gz` (version 0.2.26 as of writing) from the https://github.com/jcustenborder/kafka-connect-twitter/releases
* Extract tar.gz file outside the project
* Copy the `usr/share/kafka-connect/kafka-connect-twitter` directory in the extracted folder to a directory named `connectors`
inside the `kafka-connect` module directory. The `kafka-connect-twitter` folder contains a bunch of `jar` files.

E.g

```
kafka-connect
├── connectors
│   └── kafka-connect-twitter
|      ├── annotations-2.0.1.jar
|      ├── connect-utils-0.3.105.jar
|      └── ...
├── connect-standalone.properties
├── README.md
└── twitter.properties
```

* Update the `twitter.properties` file and replace `CONSUMER_KEY`, `CONSUMER_SECRET`, `ACCESS_TOKEN`, `ACCESS_TOKEN_SECRET` with
the generated access credentials.
* Run the following command in the terminal
`$ connect-standalone connect-standalone.properties twitter.properties`
