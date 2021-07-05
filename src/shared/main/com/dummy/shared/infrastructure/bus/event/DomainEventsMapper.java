package com.dummy.shared.infrastructure.bus.event;

import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public final class DomainEventsMapper {

    HashMap<String, Class<? extends DomainEvent>> indexedDomainEvents;

    public DomainEventsMapper() {
        Reflections reflections = new Reflections("com.dummy.vinxi");
        Set<Class<? extends DomainEvent>> classes = reflections.getSubTypesOf(DomainEvent.class);

        try {
            indexedDomainEvents = formatEvents(classes);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static List<String> scanDomainEventProducers() {
        Reflections reflections = new Reflections("com.dummy.vinxi");

        Set<Class<? extends DomainEvent>> producers = reflections.getSubTypesOf(DomainEvent.class);

        List<String> domainEventNames = new ArrayList<>();

        for (Class<?> producerClass : producers) {

            String topicName = new DomainEventSubscriberMapper(producerClass).formatKafkaTopicName();
            domainEventNames.add(topicName);
        }

        return domainEventNames;
    }

    public Class<? extends DomainEvent> forName(String name) {
        return indexedDomainEvents.get(name);
    }

    public String forClass(Class<? extends DomainEvent> domainEventClass) {
        return indexedDomainEvents.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), domainEventClass))
                .map(Map.Entry::getKey)
                .findFirst().orElse("");
    }

    private HashMap<String, Class<? extends DomainEvent>> formatEvents(
            Set<Class<? extends DomainEvent>> domainEvents)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        HashMap<String, Class<? extends DomainEvent>> events = new HashMap<>();

        for (Class<? extends DomainEvent> domainEvent : domainEvents) {
            DomainEvent nullInstance = domainEvent.getConstructor().newInstance();

            events.put((String) domainEvent.getMethod("eventName").invoke(nullInstance), domainEvent);
        }

        return events;
    }
}
