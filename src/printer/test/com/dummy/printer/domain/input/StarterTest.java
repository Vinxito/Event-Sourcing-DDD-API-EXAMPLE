package com.dummy.printer.domain.input;

import com.dummy.shared.infrastructure.spring.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class, KafkaAutoConfiguration.class})
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        value = {"com.dummy.*"})
public class StarterTest {
    public static void main(String[] args) {
        SpringApplication.run(StarterTest.class, args);
    }
}
