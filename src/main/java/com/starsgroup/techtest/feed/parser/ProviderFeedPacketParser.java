package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.domain.Operation;
import com.starsgroup.techtest.domain.PacketType;
import com.starsgroup.techtest.feed.ProviderFeedPacket;

import java.util.ArrayList;
import java.util.List;

public abstract class ProviderFeedPacketParser<T> {

    private static final char DELIMITER = '|';
    private static final char ESCAPE = '\\';

    public final T parse ( ProviderFeedPacket packet ) {
        List<String> values = getValues(packet.getContent());
        Header header = getHeader(values);
        return getEntity( header, values );
    }

    public abstract T getEntity(Header header, List<String> values);

    private Header getHeader (List<String> headerValues) {
        Long msgId = Long.valueOf(headerValues.get(0));
        Operation operation = Operation.getOperation(headerValues.get(1));
        PacketType packetType = PacketType.getPacketType(headerValues.get(2));
        Long timestamp = Long.valueOf(headerValues.get(3));
        return new Header( msgId, operation, packetType, timestamp ) ;
    }

    private List<String> getValues(String value) {
        List<String> values = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for(char current: value.toCharArray()) {
            if (current == DELIMITER && builder.length() == 0) {
                continue;
            }
            if (current == DELIMITER && builder.length() > 0) {
                if ( builder.charAt( builder.length() - 1) == ESCAPE ) {
                    builder.deleteCharAt( builder.length() - 1 );
                    continue;
                }
                values.add(builder.toString());
                builder = new StringBuilder();
                continue;
            }
            builder.append(current);
        }
        return values;
    }

    public final boolean getBoolean(String value) {
        if ("0".equals(value)) {
            return false;
        } else if ("1".equals(value)) {
            return true;
        }

        throw new IllegalArgumentException("unknown boolean string value: " + value);
    }
}
