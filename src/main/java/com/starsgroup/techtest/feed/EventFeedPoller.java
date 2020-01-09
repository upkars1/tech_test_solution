package com.starsgroup.techtest.feed;

import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;

public class EventFeedPoller {

    private KafkaConsumer<String,String> kafkaConsumer;
    private EventFeedConsumer eventFeedConsumer;
    private Duration pollTimeoutMillis;

    public EventFeedPoller(KafkaConsumer<String, String> kafkaConsumer, EventFeedConsumer eventFeedConsumer, Duration pollTimeoutMillis) {
        this.kafkaConsumer = kafkaConsumer;
        this.eventFeedConsumer = eventFeedConsumer;
        this.pollTimeoutMillis = pollTimeoutMillis;
    }

    public void poll(boolean continuously) {
        if ( continuously ) {
            pollContinuously();
        } else {
            poll();
        }
    }

    private void pollContinuously() {
        while (true) {
            poll();
        }
    }

    private void poll() {
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(pollTimeoutMillis);
        consumerRecords.forEach(record -> eventFeedConsumer.consume(record.value()));
    }
}
