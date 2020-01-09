package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.feed.formatter.JsonFormatter;

public class AbstractCouchDBDAO {

    private CouchDBAPI couchDBAPI;
    private JsonFormatter jsonFormatter;
    private String database;

    public AbstractCouchDBDAO(CouchDBAPI couchDBAPI, JsonFormatter jsonFormatter, String database) {
        this.couchDBAPI = couchDBAPI;
        this.jsonFormatter = jsonFormatter;
        this.database = database;
    }

    protected void save( Object object ) {
        couchDBAPI.save(database, jsonFormatter.toJson(object));
    }
}
