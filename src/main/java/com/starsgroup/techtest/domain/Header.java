package com.starsgroup.techtest.domain;

public class Header {

    private long msgId;
    private Operation operation;
    private PacketType type;
    private long timestamp;

    public Header(long msgId, Operation operation, PacketType type, long timestamp) {
        this.msgId = msgId;
        this.operation = operation;
        this.type = type;
        this.timestamp = timestamp;
    }

    public long getMsgId() {
        return msgId;
    }

    public Operation getOperation() {
        return operation;
    }

    public PacketType getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
