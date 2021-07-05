package com.dummy.shared.infrastructure.persistence.elasticsearch;

import net.minidev.json.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public final class ElasticsearchClient {

    private final RestHighLevelClient highLevelClient;
    private final RestClient lowLevelClient;
    private final String indexPrefix;

    public ElasticsearchClient(RestHighLevelClient highLevelClient, RestClient lowLevelClient, String indexPrefix) {
        this.highLevelClient = highLevelClient;
        this.lowLevelClient = lowLevelClient;
        this.indexPrefix = indexPrefix;
    }

    public RestHighLevelClient highLevelClient() {
        return highLevelClient;
    }

    public RestClient lowLevelClient() {
        return lowLevelClient;
    }

    public String indexPrefix() {
        return indexPrefix;
    }

    public void persist(String moduleName, HashMap<String, Serializable> plainBody) throws IOException {
        IndexRequest request = new IndexRequest(indexFor(moduleName)).source(plainBody);
        highLevelClient().index(request, RequestOptions.DEFAULT);
    }

    public String indexFor(String moduleName) {
        return String.format("%s_%s", indexPrefix(), moduleName);
    }

    public void update(String id, String moduleName, HashMap<String, Serializable> plainBody) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(indexFor(moduleName));
        updateRequest.doc(new JSONObject(plainBody));
        updateRequest.id(id);
        highLevelClient().update(updateRequest, RequestOptions.DEFAULT);
    }

    public void delete(String id, String moduleName) throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexFor(moduleName));
        request.setQuery(QueryBuilders.matchQuery("id", id));
        highLevelClient().deleteByQuery(request, RequestOptions.DEFAULT);
    }
}
