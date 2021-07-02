package com.dummy.vinxi.shared.infrastructure.persistence.elasticsearch;

import com.dummy.vinxi.shared.infrastructure.config.Parameter;
import com.dummy.vinxi.shared.infrastructure.config.ParameterNotExist;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

@Configuration
public class DeviceElasticsearchConfiguration {

    private final ResourcePatternResolver resourceResolver;
    private final Parameter parameter;

    public DeviceElasticsearchConfiguration(ResourcePatternResolver resourceResolver, Parameter parameter) {
        this.resourceResolver = resourceResolver;
        this.parameter = parameter;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() throws ParameterNotExist, IOException {
        ElasticsearchClient client = new ElasticsearchClient(
                new RestHighLevelClient(RestClient.builder(
                        new HttpHost(
                                parameter.get("PRINTER_ELASTICSEARCH_HOST"),
                                parameter.getInt("PRINTER_ELASTICSEARCH_PORT"),
                                "http"))),
                RestClient.builder(
                        new HttpHost(
                                parameter.get("PRINTER_ELASTICSEARCH_HOST"),
                                parameter.getInt("PRINTER_ELASTICSEARCH_PORT"),
                                "http")).build(),
                parameter.get("PRINTER_ELASTICSEARCH_INDEX_PREFIX"));

        generateIndexIfNotExists(client);

        return client;
    }

    private void generateIndexIfNotExists(ElasticsearchClient client) throws IOException {

        Resource[] jsonsIndexes = resourceResolver.getResources(
                String.format("classpath:database/*.json")
        );

        for (Resource jsonIndex : jsonsIndexes) {
            String indexName = Objects.requireNonNull(jsonIndex.getFilename()).replace(".json", "");

            if (!indexExists(indexName, client)) {
                String indexBody = new Scanner(
                        jsonIndex.getInputStream(),
                        "UTF-8"
                ).useDelimiter("\\A").next();

                Request request = new Request("PUT", indexName);
                request.setJsonEntity(indexBody);

                client.lowLevelClient().performRequest(request);
            }
        }
    }

    private boolean indexExists(String indexName, ElasticsearchClient client) {
        try {
            return client.highLevelClient().indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return true;
    }
}