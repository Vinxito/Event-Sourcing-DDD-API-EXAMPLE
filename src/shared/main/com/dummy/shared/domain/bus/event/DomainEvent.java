package com.dummy.shared.domain.bus.event;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

public abstract class DomainEvent {

    private String aggregateId;
    private String eventId;
    private String occurredOn;
    private String version;

    public DomainEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.version = "1";
    }

    public DomainEvent(String aggregateId, String eventId, String occurredOn, String version) {
        this.aggregateId = aggregateId;
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.version = version;
    }

    protected DomainEvent() {
    }

    public abstract String eventName();

    public abstract HashMap<String, Serializable> toPrimitives();

    public abstract DomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn,
            String version
    );

    public HashMap<String, Serializable> meta() {
        return new HashMap<String, Serializable>() {{
            try {
                put("host", InetAddress.getLocalHost());
                put("ip", InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }};
    }

    public String aggregateId() {
        return aggregateId;
    }

    public String eventId() {
        return eventId;
    }

    public String occurredOn() {
        return occurredOn;
    }

    public String version() {
        return version;
    }
}
