package com.dummy.vinxi.shared.infrastructure.bus.event.kafka;

import com.dummy.vinxi.shared.infrastructure.config.Parameter;
import com.dummy.vinxi.shared.infrastructure.config.ParameterNotExist;
import com.dummy.vinxi.shared.infrastructure.bus.event.DomainEventSubscribersMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaDomainEventBusConfiguration {

  private final DomainEventSubscribersMapper domainEventSubscribersMapper;
  private final Parameter parameter;
  private final List<NewTopic> topics = new ArrayList<>();

  public KafkaDomainEventBusConfiguration(
      DomainEventSubscribersMapper domainEventSubscribersMapper, Parameter parameter) {
    this.domainEventSubscribersMapper = domainEventSubscribersMapper;
    this.parameter = parameter;
  }

  @Bean
  public KafkaAdmin kafkaAdmin() throws ParameterNotExist {
    Map<String, Object> configs = new HashMap<>();
    configs.put(
        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.format("%s:%s", parameter.get("KAFKA_HOST"), parameter.get("KAFKA_PORT")));
    configs.put(AdminClientConfig.RETRIES_CONFIG, parameter.get("KAFKA_MAX_RETRIES"));
    return new KafkaAdmin(configs);
  }

  @Bean
  public AdminClient adminClient() throws ParameterNotExist {
    return AdminClient.create(kafkaAdmin().getConfigurationProperties());
  }

  @Bean
  public void createTopics() throws ParameterNotExist {
    domainEventSubscribersMapper
        .all()
        .forEach(
            domainEventMapper -> {
              NewTopic topic = TopicBuilder.name(domainEventMapper.formatKafkaTopicName()).build();
              topics.add(topic);
            });
    adminClient().createTopics(topics);
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() throws ParameterNotExist {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public ProducerFactory<String, String> producerFactory() throws ParameterNotExist {
    Map<String, Object> configs = new HashMap<>();
    configs.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        String.format("%s:%s", parameter.get("KAFKA_HOST"), parameter.get("KAFKA_PORT")));
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    configs.put(ProducerConfig.ACKS_CONFIG, parameter.get("KAFKA_ACKS"));
    configs.put(ProducerConfig.RETRIES_CONFIG, parameter.get("KAFKA_MAX_RETRIES"));
    configs.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, parameter.get("KAFKA_RETRY_BACKOFF_MS"));

    return new DefaultKafkaProducerFactory<>(configs);
  }
}
