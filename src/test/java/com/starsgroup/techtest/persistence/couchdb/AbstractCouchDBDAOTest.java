package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.feed.formatter.JsonFormatter;
import org.mockito.Mock;

public abstract class AbstractCouchDBDAOTest {

    protected final String databaseName = "stars_group";
    @Mock
    protected JsonFormatter formatter;
    @Mock
    protected CouchDBAPI couchDBAPI;


}
