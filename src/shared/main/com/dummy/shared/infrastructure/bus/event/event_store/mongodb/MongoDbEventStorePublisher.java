package com.dummy.shared.infrastructure.bus.event.event_store.mongodb;

import com.dummy.shared.domain.Utils;
import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.domain.bus.event.event_store.EventStorePublisher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MongoDbEventStorePublisher implements EventStorePublisher {

    private final MongoTemplate mongoClient;

    public MongoDbEventStorePublisher(MongoTemplate mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    @Transactional
    public void publish(DomainEvent domainEvent) {

        String id = domainEvent.eventId();
        String aggregateId = domainEvent.aggregateId();
        String name = domainEvent.eventName();
        String occurredOn = domainEvent.occurredOn();
        String version = domainEvent.version();
        String body = Utils.jsonEncode(domainEvent.toPrimitives());
        String meta = Utils.jsonEncode(domainEvent.meta());

        mongoClient.insert(new EventStoreMongoDocument(id, aggregateId, name, occurredOn, version, body, meta));
    }
}

