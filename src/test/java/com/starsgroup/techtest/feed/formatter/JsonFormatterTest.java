package com.starsgroup.techtest.feed.formatter;

import com.starsgroup.techtest.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class JsonFormatterTest {

    @InjectMocks
    private JsonFormatter testSubject;

    @Test
    public void shouldFormat() {
        String expected = "{\"header\":{\"msgId\":1,\"operation\":\"CREATE\",\"type\":\"EVENT\",\"timestamp\":2},\"body\":{\"name\":\"event-name-1\",\"displayed\":true,\"suspended\":false,\"eventId\":\"event-id-1\",\"category\":\"Football\",\"subCategory\":\"Champions League\",\"startTime\":3},\"markets\":[]}";
        Header header = new Header( 1L , Operation.CREATE, PacketType.EVENT, 2L );
        EventBody body = new EventBody.Builder().withCategory( "Football" ).withDisplayed(true).withEventId("event-id-1")
                .withName("event-name-1").withStartTime(3L).withSubCategory("Champions League").withSuspended(false).build();
        Event event = new Event(header , body);
        String json = testSubject.toJson(event);
        assertEquals(expected, json);
    }
}
