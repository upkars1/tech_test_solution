package com.starsgroup.techtest.feed.kafka_streams_impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class FeedConsumerIntegrationTest extends AbstractFeedConsumerTest {

    private static final String TOPIC_NAME = "tech_test";
    private static final int POLL_DURATION_TIMEOUT_MILLIS = 500;

    private boolean isRead = false;

    @Test
    public void shouldConsumeMessage() throws InterruptedException {

        String randomID = UUID.randomUUID().toString();
        String packet = "|0|create|event|1497359166352|"+ randomID +"|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|";

        Thread consumer = new Thread( () -> isRead = testMessageRead(packet));
        Thread producer = new Thread( () -> sendMessage(packet));

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();

        assertTrue( "Example message was not read by Kafka Consumer." , isRead );
    }



    private Properties getKafkaProducerConfig() {
        Properties props = getKafkaCommonConfig();
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("transactional.id", "test");
        return props;
    }



    private boolean testMessageRead(String packet) {
        KafkaConsumer<String, String> consumer = getKafkaConsumer();
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        boolean testMessageRead = false;
        int max_retrievals = Integer.MAX_VALUE;
        int retrievals = 0;
        while ( !testMessageRead && retrievals < max_retrievals  ) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(POLL_DURATION_TIMEOUT_MILLIS));
            retrievals++;
            for (ConsumerRecord record: records) {
                if (record.value().toString().equals(packet)) {
                    testMessageRead = true;
                    break;
                }
            }
        }
        return testMessageRead;
    }

    private void sendMessage(String packet) {
            KafkaProducer producer = new KafkaProducer(getKafkaProducerConfig());
            producer.initTransactions();
            producer.beginTransaction();
            producer.send(new ProducerRecord(TOPIC_NAME, packet));
            producer.commitTransaction();
            producer.flush();
            producer.close();
    }

}
