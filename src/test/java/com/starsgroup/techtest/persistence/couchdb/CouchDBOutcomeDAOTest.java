package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CouchDBOutcomeDAOTest extends AbstractCouchDBDAOTest {

    private CouchDBOutcomeDAO testSubject;

    @Before
    public void setUp() {
        testSubject = new CouchDBOutcomeDAO( couchDBAPI, formatter, databaseName );
    }

    @Test
    public void shouldSave() {
        Header header = new Header( 1L, Operation.UPDATE, PacketType.OUTCOME, 2L );
        OutcomeBody body = new OutcomeBody.Builder().build();
        Outcome outcome = new Outcome( header, body );
        String json = "outcome-as-json";

        when(formatter.toJson(outcome)).thenReturn(json);

        testSubject.save(outcome);

        verify(couchDBAPI).save(eq(databaseName), eq(json));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldFailToSaveWhenOperationIsCreateType() {
        Header header = new Header( 1L, Operation.CREATE, PacketType.OUTCOME, 2L );
        OutcomeBody body = new OutcomeBody.Builder().build();
        Outcome outcome = new Outcome( header , body);
        testSubject.save(outcome);
    }
}
