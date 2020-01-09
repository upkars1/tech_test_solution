package com.starsgroup.techtest.feed.kafka_streams_impl;

import com.starsgroup.techtest.feed.EventFeedPoller;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import com.starsgroup.techtest.feed.formatter.JsonFormatter;
import com.starsgroup.techtest.feed.handler.ProviderFeedPacketHandlerFactory;
import com.starsgroup.techtest.persistence.couchdb.CouchDBAPI;
import com.starsgroup.techtest.persistence.couchdb.CouchDBEventDAO;
import com.starsgroup.techtest.persistence.couchdb.CouchDBMarketDAO;
import com.starsgroup.techtest.persistence.couchdb.CouchDBOutcomeDAO;
import feign.Feign;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.time.Duration;

import static java.util.Arrays.*;

public class FeedConsumerSystemTest extends AbstractFeedConsumerTest {

    private final String couchDBUrl = "http://127.0.0.1:5984";
    private final String databaseName = "stars_group";
    private final Duration pollTimeoutMillis = Duration.ofMillis(500);
    private final int lengthOfExecutionInSeconds = 60;

    @Test
    public void shouldReadFromMessageQueueAndWriteToNoSQLStore() throws InterruptedException {
        CouchDBAPI couchDBAPI = Feign.builder().target(CouchDBAPI.class, couchDBUrl);
        JsonFormatter jsonFormatter = new JsonFormatter();
        CouchDBEventDAO couchDBEventDAO = new CouchDBEventDAO(couchDBAPI, jsonFormatter, databaseName);
        CouchDBMarketDAO couchDBMarketDAO = new CouchDBMarketDAO(couchDBAPI, jsonFormatter, databaseName);
        CouchDBOutcomeDAO couchDBOutcomeDAO = new CouchDBOutcomeDAO(couchDBAPI, jsonFormatter, databaseName);
        ProviderFeedPacketHandlerFactory packetHandlerFactory = new ProviderFeedPacketHandlerFactory();
        EventFeedConsumer eventFeedConsumer = new EventFeedConsumer(couchDBEventDAO, couchDBMarketDAO, couchDBOutcomeDAO, packetHandlerFactory);
        KafkaConsumer<String, String> kafkaConsumer = getKafkaConsumer();
        kafkaConsumer.subscribe(asList("tech_test"));
        EventFeedPoller eventFeedPoller = new EventFeedPoller(kafkaConsumer, eventFeedConsumer, pollTimeoutMillis);
        Thread thread = new Thread( () -> eventFeedPoller.poll(true) );
        thread.start();
        // execute for a minute - couchdb data can be viewed off line separately via the CouchDB UI  http://127.0.0.1:5984/_utils/
        int timer = lengthOfExecutionInSeconds;
        while ( timer > 0 ) {
            Thread.sleep(1000);
            timer--;
        }
    }
}
