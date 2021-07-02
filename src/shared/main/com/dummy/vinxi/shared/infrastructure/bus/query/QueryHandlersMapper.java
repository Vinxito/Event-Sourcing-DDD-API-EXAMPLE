package com.dummy.vinxi.shared.infrastructure.bus.query;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import com.dummy.vinxi.shared.domain.bus.query.Query;
import com.dummy.vinxi.shared.domain.bus.query.QueryHandler;
import com.dummy.vinxi.shared.domain.bus.query.QueryNotRegisteredError;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

@Service
public final class QueryHandlersMapper {

    @SuppressWarnings("rawtypes")
    HashMap<Class<? extends Query>, Class<? extends QueryHandler>> indexedQueryHandlers;

    @SuppressWarnings("rawtypes")
    public QueryHandlersMapper() {

        Reflections reflections = new Reflections("com.dummy.vinxi");
        Set<Class<? extends QueryHandler>> classes = reflections.getSubTypesOf(QueryHandler.class);

        indexedQueryHandlers = formatHandlers(classes);
    }

    @SuppressWarnings("rawtypes")
    public Class<? extends QueryHandler> search(Class<? extends Query> queryClass) throws QueryNotRegisteredError {

        Class<? extends QueryHandler> queryHandlerClass = indexedQueryHandlers.get(queryClass);

        if (null == queryHandlerClass) {
            throw new QueryNotRegisteredError(queryClass);
        }

        return queryHandlerClass;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private HashMap<Class<? extends Query>, Class<? extends QueryHandler>> formatHandlers(
            Set<Class<? extends QueryHandler>> queryHandlers) {

        HashMap<Class<? extends Query>, Class<? extends QueryHandler>> handlers = new HashMap<>();

        for (Class<? extends QueryHandler> handler : queryHandlers) {
            ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];

            Class<? extends Query> queryClass = (Class<? extends Query>) paramType.getActualTypeArguments()[0];

            handlers.put(queryClass, handler);
        }

        return handlers;
    }
}
