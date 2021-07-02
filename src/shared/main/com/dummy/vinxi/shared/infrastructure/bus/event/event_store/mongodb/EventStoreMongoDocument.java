package com.dummy.vinxi.shared.infrastructure.bus.event.event_store.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "event_store")
class EventStoreMongoDocument {
    @Id
    private String id;
    @Field("aggregate_id")
    private String aggregateId;
    private String name;
    @Field(value = "occurred_on", targetType = FieldType.TIMESTAMP)
    private String occurredOn;
    private String version;
    private String body;
    private String meta;

    public EventStoreMongoDocument(String id,
                                   String aggregateId,
                                   String name,
                                   String occurredOn,
                                   String version,
                                   String body,
                                   String meta) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.name = name;
        this.occurredOn = occurredOn;
        this.version = version;
        this.body = body;
        this.meta = meta;
    }

    public String id() {
        return id;
    }

    public String aggregateId() {
        return aggregateId;
    }

    public String name() {
        return name;
    }

    public String occurredOn() {
        return occurredOn;
    }

    public String version() {
        return version;
    }

    public String body() {
        return body;
    }
}
