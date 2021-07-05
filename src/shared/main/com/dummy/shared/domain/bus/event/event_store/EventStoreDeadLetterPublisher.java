package com.dummy.shared.domain.bus.event.event_store;

import com.dummy.shared.domain.bus.event.DomainEvent;

import java.util.List;

public interface EventStoreDeadLetterPublisher {
    void publish(List<DomainEvent> domainEvents);
}
