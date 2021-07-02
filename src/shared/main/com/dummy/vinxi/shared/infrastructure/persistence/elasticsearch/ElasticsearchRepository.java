package com.dummy.vinxi.shared.infrastructure.persistence.elasticsearch;

import com.dummy.vinxi.shared.domain.criteria.Criteria;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ElasticsearchRepository<T> {

    private final ElasticsearchClient client;
    private final ElasticsearchCriteriaConverter criteriaConverter;

    public ElasticsearchRepository(ElasticsearchClient client) {
        this.client = client;
        this.criteriaConverter = new ElasticsearchCriteriaConverter();
    }

    abstract protected String moduleName();

    protected Optional<T> searchById(Function<Map<String, Object>, T> unserializer, String id) {

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("id", id);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);

        SearchRequest request = new SearchRequest(client.indexFor(moduleName())).source(sourceBuilder);

        try {
            SearchResponse response = client.highLevelClient().search(request, RequestOptions.DEFAULT);
            return Arrays.stream(response.getHits().getHits())
                    .map(hit -> unserializer.apply(hit.getSourceAsMap())).findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    protected List<T> searchAllInElastic(Function<Map<String, Object>, T> unserializer, SearchSourceBuilder sourceBuilder) {

        SearchRequest request = new SearchRequest(client.indexFor(moduleName())).source(sourceBuilder);

        try {
            SearchResponse response = client.highLevelClient().search(request, RequestOptions.DEFAULT);

            return Arrays.stream(response.getHits().getHits())
                    .map(hit -> unserializer.apply(hit.getSourceAsMap()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    protected List<T> searchByCriteria(Criteria criteria, Function<Map<String, Object>, T> unserializer) {
        return searchAllInElastic(unserializer, criteriaConverter.convert(criteria));
    }

    protected void persist(HashMap<String, Serializable> plainBody) {
        try {
            client.persist(moduleName(), plainBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void actualize(String id, HashMap<String, Serializable> plainBody) {
        try {
            QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("id", id);
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(matchQueryBuilder);
            SearchRequest request = new SearchRequest(client.indexFor(moduleName())).source(sourceBuilder);

            SearchResponse response = client.highLevelClient().search(request, RequestOptions.DEFAULT);

            client.update(Arrays.stream(response.getHits().getHits()).findFirst().get().getId(), moduleName(), plainBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void remove(String id) {
        try {
            client.delete(id, moduleName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
