package com.dummy.shared.infrastructure.bus.event.event_store.mongodb;

import com.dummy.shared.infrastructure.bus.event.DomainEventsMapper;
import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.domain.EventStoreConsumer;
import com.dummy.shared.domain.Utils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MongoDbEventStoreConsumer implements EventStoreConsumer {

    private final MongoTemplate mongoClient;
    private final DomainEventsMapper domainEventsMapper;

    public MongoDbEventStoreConsumer(MongoTemplate mongoClient, DomainEventsMapper domainEventsMapper) {
        this.mongoClient = mongoClient;
        this.domainEventsMapper = domainEventsMapper;
    }

    @Override
    public List<DomainEvent> consume(String id) throws Exception {
        return retrieveEvents(id);
    }

    @Transactional(readOnly = true)
    public List<DomainEvent> retrieveEvents(String aggregateId) throws Exception {

        Query query = new Query();
        query.addCriteria(Criteria.where("aggregate_id").regex(aggregateId));
        query.with(Sort.by(Sort.Direction.ASC, "occurred_on"));
        List<EventStoreMongoDocument> nativeQueryResults = mongoClient.find(query, EventStoreMongoDocument.class);

        List<DomainEvent> events = new ArrayList<>();

        try {
            for (EventStoreMongoDocument eventStoreDto : nativeQueryResults) {
                events.add(executeSubscribers(
                        eventStoreDto.id(),
                        eventStoreDto.aggregateId(),
                        eventStoreDto.name(),
                        eventStoreDto.body(),
                        Utils.stringToDate(eventStoreDto.occurredOn()),
                        eventStoreDto.version()
                ));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new Exception(String.format("Error recovering events with id: %s on event store", aggregateId));
        }

        return events;
    }

    private DomainEvent executeSubscribers(String id, String aggregateId, String eventName, String body, Timestamp occurredOn, String version)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<? extends DomainEvent> domainEventClass = domainEventsMapper.forName(eventName);

        DomainEvent nullInstance = domainEventClass.getConstructor().newInstance();

        Method fromPrimitivesMethod = domainEventClass.getMethod(
                "fromPrimitives",
                String.class,
                HashMap.class,
                String.class,
                String.class,
                String.class
        );

        Object domainEvent = fromPrimitivesMethod.invoke(
                nullInstance,
                aggregateId,
                Utils.jsonDecode(body),
                id,
                Utils.dateToString(occurredOn),
                version
        );

        return (DomainEvent) domainEvent;
    }


}
