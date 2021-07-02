package com.dummy.vinxi.shared.domain;

import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {

    protected List<DomainEvent> domainEvents = new ArrayList<>();

    public AggregateRoot() {
    }

    public AggregateRoot(EventStoreConsumer consumer, String aggregateId) {
        try {
            List<DomainEvent> domainEvents = consumer.consume(aggregateId);
            domainEvents.forEach(event -> {
                apply(event);
            });
        } catch (Exception exception) {
            throw new AggregateRootNotExist(aggregateId);
        }
    }

    private void apply(DomainEvent event) {
        try {
            Method method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new UnsupportedOperationException(
                    String.format("Aggregate '%s' doesn't apply event type '%s'", getClass(), event.getClass()),
                    exception);
        }
    }

    final public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = domainEvents;
        domainEvents = Collections.emptyList();
        return events;
    }

    final protected void record(DomainEvent event) {
        domainEvents.add(event);
    }
}

