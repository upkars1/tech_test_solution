package com.starsgroup.techtest.persistence.couchdb;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CouchDBAPI {

    @RequestLine("POST /{db}")
    @Body("{body}")
    @Headers("Content-Type: application/json")
    void save(@Param("db") String database, @Param("body") String entityAsJson );
}
