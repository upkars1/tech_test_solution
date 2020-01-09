package com.starsgroup.techtest.persistence.couchdb;

import com.starsgroup.techtest.domain.Outcome;
import com.starsgroup.techtest.feed.formatter.JsonFormatter;
import com.starsgroup.techtest.persistence.OutcomeDAO;


import static com.starsgroup.techtest.domain.Operation.UPDATE;

public class CouchDBOutcomeDAO extends AbstractCouchDBDAO implements OutcomeDAO {

    public CouchDBOutcomeDAO(CouchDBAPI couchDBAPI, JsonFormatter jsonFormatter, String database) {
        super(couchDBAPI, jsonFormatter, database);
    }

    @Override
    public void save(Outcome outcome) {
        if (outcome.getHeader().getOperation().equals(UPDATE)) {
            super.save(outcome);
        } else {
            throw new IllegalArgumentException("A new outcome must be saved as part of the Event document.");
        }
    }
}
