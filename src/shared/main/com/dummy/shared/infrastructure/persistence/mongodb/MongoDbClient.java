package com.dummy.shared.infrastructure.persistence.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDbClient {

    public MongoClient client(String connection) {
        ConnectionString connectionString = new ConnectionString(String.format("mongodb://%s?authSource=admin", connection));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
