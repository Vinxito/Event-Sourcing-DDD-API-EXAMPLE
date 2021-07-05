package com.dummy.shared.infrastructure.bus.event.kafka;

import com.dummy.shared.infrastructure.bus.event.DomainEventJsonDeserializer;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.infrastructure.bus.event.DomainEventSubscribersMapper;
import com.dummy.shared.domain.Utils;
import com.dummy.shared.domain.bus.event.event_store.EventStoreDeadLetterPublisher;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaDomainEventConsumer {

    private final DomainEventSubscribersMapper domainEventSubscribersMapper;
    private final EventStoreDeadLetterPublisher deadLetterPublisher;
    private final DomainEventJsonDeserializer deserializer;
    private final ApplicationContext context;

    public KafkaDomainEventConsumer(DomainEventJsonDeserializer deserializer, ApplicationContext context,
                                    DomainEventSubscribersMapper domainEventSubscribersMapper, EventStoreDeadLetterPublisher deadLetterPublisher) {
        this.deserializer = deserializer;
        this.context = context;
        this.deadLetterPublisher = deadLetterPublisher;
        this.domainEventSubscribersMapper = domainEventSubscribersMapper;
    }

    @KafkaListener(topicPattern = "dummy.*", groupId = "default_group")
    public void consumer(String message) throws Exception {

        DomainEvent domainEvent = deserializer.deserialize(message);

        domainEventSubscribersMapper.domainEvents().entrySet().stream().forEach(mapper -> {
            mapper.getValue().subscribedEvents().forEach(subscribedEvent -> {
                if (subscribedEvent.getName().equals(domainEvent.getClass().getName())) {

                    String[] topicParts = mapper.getKey().getName().split("\\.");
                    String subscriberName = Utils.toSnake(topicParts[topicParts.length - 1]);

                    try {
                        Object subscriber = context.getBean(Utils.toCamelFirstLower(subscriberName));
                        Method subscriberOnMethod = subscriber.getClass().getMethod("on", domainEvent.getClass());
                        subscriberOnMethod.invoke(subscriber, domainEvent);
                    } catch (Exception error) {
                        deadLetterPublisher.publish(Collections.singletonList(domainEvent));
                    }
                }
            });
        });
    }

    @Bean
    public ConsumerFactory<? super String, ? super Object> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "default_group");
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
