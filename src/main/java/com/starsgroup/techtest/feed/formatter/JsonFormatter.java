package com.starsgroup.techtest.feed.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonFormatter {

    private ObjectMapper mapper = new ObjectMapper();

    public String toJson ( Object object )  {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonFormattingException(e);
        }
    }
}
