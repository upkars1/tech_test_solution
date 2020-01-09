package com.starsgroup.techtest.feed;

import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventFeedPollerTest {

    private EventFeedPoller testSubject;

    @Mock
    private KafkaConsumer<String, String> kafkaConsumer;

    @Mock
    private EventFeedConsumer eventFeedConsumer;

    @Captor
    private ArgumentCaptor<String> eventFeedConsumerArgumentCaptor;

    private Duration timeoutInMillis = Duration.ofMillis(500);


    @Before
    public void setUp() {
        testSubject = new EventFeedPoller( kafkaConsumer , eventFeedConsumer , timeoutInMillis );
    }

    @Test
    public void shouldSendToConsumer() {

        ConsumerRecord<String, String> recordOne = new ConsumerRecord<>( "tech_test" , 0 , 0L, "key1", "data1" );
        ConsumerRecord<String, String> recordTwo = new ConsumerRecord<>( "tech_test" , 0 , 10L, "key2", "data2" );
        ConsumerRecord<String, String> recordThree = new ConsumerRecord<>( "tech_test" , 0 , 20L, "key3", "data3" );

        TopicPartition partition = new TopicPartition("tech_test", 0);
        Map<TopicPartition, List<ConsumerRecord>> records = new HashMap<>();
        records.put(partition, asList( recordOne , recordTwo , recordThree ));
        ConsumerRecords consumerRecords = new ConsumerRecords(records);

        when( kafkaConsumer.poll(timeoutInMillis)).thenReturn( consumerRecords );

        testSubject.poll(false);

        verify( eventFeedConsumer, times(3) ).consume( eventFeedConsumerArgumentCaptor.capture() );

        List<String> packetData = eventFeedConsumerArgumentCaptor.getAllValues();

        assertEquals( 3 , packetData.size() );
        assertEquals( "data1", packetData.get(0));
        assertEquals( "data2", packetData.get(1));
        assertEquals( "data3", packetData.get(2));
    }
}
