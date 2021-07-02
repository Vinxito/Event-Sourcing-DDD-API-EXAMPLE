package com.dummy.vinxi.shared.infrastructure.bus.event.kafka;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;
import com.dummy.vinxi.shared.domain.bus.event.EventBus;
import com.dummy.vinxi.shared.domain.bus.event.event_store.EventStoreDeadLetterPublisher;
import com.dummy.vinxi.shared.domain.bus.event.event_store.EventStorePublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;

import java.util.Collections;
import java.util.List;

@Primary
@Service
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaDomainEventBus implements EventBus {

    private final KafkaDomainEventPublisher publisher;
    private final EventStorePublisher postgreSqlEventStorePublisher;
    private final EventStoreDeadLetterPublisher failoverPublisher;

    public KafkaDomainEventBus(KafkaDomainEventPublisher publisher, EventStoreDeadLetterPublisher failoverPublisher,
                               EventStorePublisher postgreSqlEventStorePublisher) {
        this.publisher = publisher;
        this.postgreSqlEventStorePublisher = postgreSqlEventStorePublisher;
        this.failoverPublisher = failoverPublisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    private void publish(DomainEvent domainEvent) {
        try {
            this.publisher.publish(domainEvent);
            this.postgreSqlEventStorePublisher.publish(domainEvent);
        } catch (Exception error) {
            failoverPublisher.publish(Collections.singletonList(domainEvent));
        }
    }
}
