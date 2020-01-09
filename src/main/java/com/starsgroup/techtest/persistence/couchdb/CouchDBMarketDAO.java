package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.Market;
import com.starsgroup.techtest.feed.formatter.JsonFormatter;
import com.starsgroup.techtest.persistence.MarketDAO;

import java.io.IOException;

import static com.starsgroup.techtest.domain.Operation.*;

public class CouchDBMarketDAO extends AbstractCouchDBDAO implements MarketDAO {

    public CouchDBMarketDAO(CouchDBAPI couchDBAPI, JsonFormatter jsonFormatter, String database) {
        super(couchDBAPI, jsonFormatter, database);
    }

    @Override
    public void save(Market market) throws IOException {
        if ( market.getHeader().getOperation().equals(UPDATE) ) {
            super.save(market);
        } else {
            throw new IllegalArgumentException("A new market must be saved as part of the Event document.");
        }
    }
}
