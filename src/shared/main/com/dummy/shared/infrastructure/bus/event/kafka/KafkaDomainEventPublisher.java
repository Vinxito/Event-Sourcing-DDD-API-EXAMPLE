package com.dummy.shared.infrastructure.bus.event.kafka;

import com.dummy.shared.infrastructure.bus.event.DomainEventJsonSerializer;
import com.dummy.shared.infrastructure.bus.event.DomainEventSubscriberMapper;
import com.dummy.shared.infrastructure.spring.Service;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.infrastructure.bus.event.DomainEventSubscribersMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public final class KafkaDomainEventPublisher {

    private final KafkaTemplate kafkaTemplate;
    private final DomainEventSubscribersMapper domainEventSubscribersMapper;
    private final Environment environment;
    private String publishDomainEventName = "";


    public KafkaDomainEventPublisher(KafkaTemplate kafkaTemplate, DomainEventSubscribersMapper domainEventSubscribersMapper, Environment environment) {
        this.kafkaTemplate = kafkaTemplate;
        this.domainEventSubscribersMapper = domainEventSubscribersMapper;
        this.environment = environment;
    }

    public void publish(DomainEvent domainEvent) {
        String serializedDomainEvent = DomainEventJsonSerializer.serialize(domainEvent, environment);

        publishDomainEventName = "";

        domainEventSubscribersMapper.domainEvents().entrySet().stream().forEach(mapper -> {
            mapper.getValue().subscribedEvents().forEach(subscribedEvent -> {
                if (subscribedEvent.getName().equals(domainEvent.getClass().getName())) {
                    publishDomainEventName = new DomainEventSubscriberMapper(mapper.getKey()).formatKafkaTopicName();
                }
            });
        });
        kafkaTemplate.send(publishDomainEventName, domainEvent.eventName(), serializedDomainEvent);
    }
}
