package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.Event;
import com.starsgroup.techtest.feed.formatter.JsonFormatter;
import com.starsgroup.techtest.persistence.EventDAO;

public class CouchDBEventDAO extends AbstractCouchDBDAO implements EventDAO {

    public CouchDBEventDAO(CouchDBAPI couchDBAPI, JsonFormatter jsonFormatter, String database) {
        super(couchDBAPI, jsonFormatter, database);
    }

    @Override
    public void save(Event unsaved) {
        super.save(unsaved);
    }
}
