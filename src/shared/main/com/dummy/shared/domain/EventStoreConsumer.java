package com.dummy.shared.domain;

import com.dummy.shared.domain.bus.event.DomainEvent;

import java.util.List;

public interface EventStoreConsumer {
    List<DomainEvent> consume(String id) throws Exception;
}
