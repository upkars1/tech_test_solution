package com.starsgroup.techtest.feed.kafka_streams_impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FeedConsumerIntegrationTest {

    private static final String TOPIC_NAME = "tech_test";
    private static final int POLL_DURATION_TIMEOUT_MILLIS = 500;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String PACKET = "|0|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|";
    private boolean isRead = false;

    @Test
    public void shouldConsumeMessage() throws InterruptedException {

        Thread consumer = new Thread( () -> isRead = testMessageRead());
        Thread producer = new Thread( () -> sendMessage());

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();

        assertTrue( "Example message was not read by Kafka Consumer." , isRead );
    }

    private Properties getKafkaConsumerConfig() {
        Properties props = getKafkaCommonConfig();
        props.setProperty("auto.offset.reset", "earliest");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    private Properties getKafkaProducerConfig() {
        Properties props = getKafkaCommonConfig();
        //props.setProperty("auto.offset.reset", "latest");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("transactional.id", "test");
        return props;
    }

    private Properties getKafkaCommonConfig() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        return props;
    }

    private boolean testMessageRead() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getKafkaConsumerConfig());
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        boolean testMessageRead = false;
        int max_retrievals = 5;
        int retrievals = 0;
        while ( !testMessageRead && retrievals < max_retrievals  ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(POLL_DURATION_TIMEOUT_MILLIS));
            retrievals++;
            for (ConsumerRecord record: records) {
                if (record.value().toString().equals(PACKET)) {
                    testMessageRead = true;
                    break;
                }
            }
        }
        return testMessageRead;
    }

    private void sendMessage() {
            KafkaProducer producer = new KafkaProducer(getKafkaProducerConfig());
            producer.initTransactions();
            producer.beginTransaction();
            producer.send(new ProducerRecord(TOPIC_NAME, PACKET));
            producer.commitTransaction();
            producer.flush();
            producer.close();
    }

}
