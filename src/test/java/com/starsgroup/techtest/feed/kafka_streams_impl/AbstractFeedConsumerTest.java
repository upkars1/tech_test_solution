package com.starsgroup.techtest.feed.kafka_streams_impl;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public abstract class AbstractFeedConsumerTest {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return new KafkaConsumer( getKafkaConsumerConfig() );
    }

    private Properties getKafkaConsumerConfig() {
        Properties props = getKafkaCommonConfig();
        props.setProperty("auto.offset.reset", "earliest");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public Properties getKafkaCommonConfig() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        return props;
    }
}
