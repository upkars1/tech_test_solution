package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CouchDBEventDAOTest extends AbstractCouchDBDAOTest {

    private CouchDBEventDAO testSubject;

    @Before
    public void setUp() {
        testSubject = new CouchDBEventDAO( couchDBAPI , formatter , databaseName);
    }

    @Test
    public void shouldSave() {
        Header header = new Header( 1L, Operation.CREATE,  PacketType.EVENT, 2L );
        EventBody body = new EventBody.Builder().build();
        Event event = new Event( header, body );
        String eventAsJson = "event-as-json";

        when(formatter.toJson(event)).thenReturn(eventAsJson);

        testSubject.save( event );

        verify(couchDBAPI).save( eq(databaseName), eq(eventAsJson));
    }
}
