package com.dummy.shared.infrastructure.bus.event;

import com.dummy.shared.domain.Utils;
import com.dummy.shared.domain.bus.event.DomainEvent;

import java.util.List;

public final class DomainEventSubscriberMapper {

    private final Class<?> subscriberClass;
    private List<Class<? extends DomainEvent>> subscribedEvents;

    public DomainEventSubscriberMapper(Class<?> subscriberClass) {
        this.subscriberClass = subscriberClass;
    }

    public DomainEventSubscriberMapper(Class<?> subscriberClass, List<Class<? extends DomainEvent>> subscribedEvents) {
        this.subscriberClass = subscriberClass;
        this.subscribedEvents = subscribedEvents;
    }

    public List<Class<? extends DomainEvent>> subscribedEvents() {
        return subscribedEvents;
    }

    public String contextName() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[5];
    }

    public String moduleName() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[6];
    }

    public String className() {
        String[] nameParts = subscriberClass.getName().split("\\.");

        return nameParts[nameParts.length - 1];
    }

    public String formatKafkaTopicName() {
        return String.format("dummy.vinxi.%s", Utils.toSnake(className()));
    }
}
