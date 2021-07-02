package com.dummy.vinxi.shared.domain.bus.event.event_store;

import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;

public interface EventStorePublisher {
    void publish(DomainEvent domainEvent) throws Exception;
}
