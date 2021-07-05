package com.dummy.shared.domain.bus.event.event_store;

import com.dummy.shared.domain.bus.event.DomainEvent;

public interface EventStorePublisher {
    void publish(DomainEvent domainEvent) throws Exception;
}
