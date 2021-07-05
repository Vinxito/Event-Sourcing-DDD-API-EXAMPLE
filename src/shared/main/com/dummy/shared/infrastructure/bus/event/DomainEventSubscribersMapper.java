package com.dummy.shared.infrastructure.bus.event;

import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEventSubscriber;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Service
public final class DomainEventSubscribersMapper {

    HashMap<Class<?>, DomainEventSubscriberMapper> domainEventMapper;

    public DomainEventSubscribersMapper(HashMap<Class<?>, DomainEventSubscriberMapper> domainEventMapper) {
        this.domainEventMapper = domainEventMapper;
    }

    public DomainEventSubscribersMapper() {
        this(scanDomainEventSubscribers());
    }

    private static HashMap<Class<?>, DomainEventSubscriberMapper> scanDomainEventSubscribers() {
        Reflections reflections = new Reflections("com.dummy.vinxi");
        Set<Class<?>> subscribers = reflections.getTypesAnnotatedWith(DomainEventSubscriber.class);

        HashMap<Class<?>, DomainEventSubscriberMapper> subscribersInformation = new HashMap<>();

        for (Class<?> subscriberClass : subscribers) {
            DomainEventSubscriber annotation = subscriberClass.getAnnotation(DomainEventSubscriber.class);

            subscribersInformation.put(
                    subscriberClass,
                    new DomainEventSubscriberMapper(subscriberClass, Arrays.asList(annotation.value()))
            );
        }

        return subscribersInformation;
    }

    public Collection<DomainEventSubscriberMapper> all() {
        return domainEventMapper.values();
    }

    public HashMap<Class<?>, DomainEventSubscriberMapper> domainEvents() {
        return domainEventMapper;
    }
}
