package com.dummy.vinxi.printer.device.infrastruture;

import com.dummy.vinxi.shared.infrastructure.config.Parameter;
import com.dummy.vinxi.shared.infrastructure.config.ParameterNotExist;
import com.dummy.vinxi.shared.infrastructure.persistence.mongodb.MongoDbClient;
import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class PrinterMongoDbConfiguration extends AbstractMongoClientConfiguration {

  private final Parameter parameter;

  public PrinterMongoDbConfiguration(Parameter parameter) {
    this.parameter = parameter;
  }

  @Override
  protected String getDatabaseName() {
    try {
      return parameter.get("PRINTER_MONGO_DATABASE_NAME");
    } catch (ParameterNotExist parameterNotExist) {
      parameterNotExist.printStackTrace();
    }
    return "";
  }

  @Override
  public Collection getMappingBasePackages() {
    return Collections.singleton("com.dummy");
  }

  @Bean
  public MongoClient reactiveMongoClient() {
    try {
      return new MongoDbClient()
          .client(
              String.format(
                  "%s:%s@%s:%s/%s",
                  parameter.get("PRINTER_MONGO_DATABASE_USER"),
                  parameter.get("PRINTER_MONGO_DATABASE_PASSWORD"),
                  parameter.get("PRINTER_MONGO_DATABASE_HOST"),
                  parameter.get("PRINTER_MONGO_DATABASE_PORT"),
                  getDatabaseName()));
    } catch (ParameterNotExist parameterNotExist) {
      parameterNotExist.printStackTrace();
    }

    return null;
  }

  @Bean
  @Primary
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(reactiveMongoClient(), getDatabaseName());
  }
}
