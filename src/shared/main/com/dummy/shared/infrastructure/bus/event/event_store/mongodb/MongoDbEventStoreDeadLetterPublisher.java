package com.dummy.shared.infrastructure.bus.event.event_store.mongodb;

import com.dummy.shared.domain.Utils;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.domain.bus.event.event_store.EventStoreDeadLetterPublisher;
import com.dummy.shared.infrastructure.spring.Service;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MongoDbEventStoreDeadLetterPublisher implements EventStoreDeadLetterPublisher {

    private final MongoTemplate mongoClient;

    public MongoDbEventStoreDeadLetterPublisher(MongoTemplate mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void publish(List<DomainEvent> domainEvents) {
        domainEvents.forEach(this::publish);
    }

    @Transactional
    public void publish(DomainEvent domainEvent) {

        String id = domainEvent.eventId();
        String aggregateId = domainEvent.aggregateId();
        String name = domainEvent.eventName();
        String occurredOn = domainEvent.occurredOn();
        String version = domainEvent.version();
        String body = Utils.jsonEncode(domainEvent.toPrimitives());
        String meta = Utils.jsonEncode(domainEvent.meta());

        mongoClient.insert(new DeadLetterEventStoreMongoDocument(id, aggregateId, name, occurredOn, version, body, meta));
    }

    @Document(collection = "event_store_dead_letter")
    class DeadLetterEventStoreMongoDocument {
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

        public DeadLetterEventStoreMongoDocument(String id,
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
    }
}
