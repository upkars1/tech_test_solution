package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CouchDBMarketDAOTest extends AbstractCouchDBDAOTest {

    private CouchDBMarketDAO testSubject;

    @Before
    public void setUp() {
        testSubject = new CouchDBMarketDAO( couchDBAPI , formatter , databaseName);
    }

    @Test
    public void shouldSave() throws IOException {
        Header header = new Header( 1L, Operation.UPDATE,  PacketType.MARKET, 2L );
        MarketBody body = new MarketBody.Builder().build();
        Market market = new Market( header, body );
        String marketAsJson = "market-as-json";

        when(formatter.toJson(market)).thenReturn(marketAsJson);

        testSubject.save( market );

        verify(couchDBAPI).save( eq(databaseName), eq(marketAsJson));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToSaveMarketWhenOperationIsCreateType() throws IOException {
        Header header = new Header( 1L, Operation.CREATE, PacketType.MARKET, 2L );
        MarketBody body = new MarketBody.Builder().build();
        Market market = new Market( header , body );

        testSubject.save( market );
    }

}
