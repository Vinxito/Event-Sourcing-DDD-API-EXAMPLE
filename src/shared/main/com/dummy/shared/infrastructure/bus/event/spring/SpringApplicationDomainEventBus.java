package com.dummy.shared.infrastructure.bus.event.spring;

import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.domain.bus.event.EventBus;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
public class SpringApplicationDomainEventBus implements EventBus {

    private final ApplicationEventPublisher publisher;

    public SpringApplicationDomainEventBus(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(final List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    public void publish(final DomainEvent event) {
        this.publisher.publishEvent(event);
    }
}
