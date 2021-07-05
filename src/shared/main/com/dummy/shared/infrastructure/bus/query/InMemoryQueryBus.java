package com.dummy.shared.infrastructure.bus.query;

import com.dummy.shared.domain.bus.query.*;
import com.dummy.shared.infrastructure.spring.Service;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryQueryBus implements QueryBus {

    private final QueryHandlersMapper queryHandlers;
    private final ApplicationContext context;

    public InMemoryQueryBus(QueryHandlersMapper queryHandlers, ApplicationContext context) {
        this.queryHandlers = queryHandlers;
        this.context = context;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Response ask(Query query) throws QueryHandlerExecutionError {
        try {
            Class<? extends QueryHandler> queryHandlerClass = queryHandlers.search(query.getClass());

            QueryHandler handler = context.getBean(queryHandlerClass);

            return handler.handle(query);
        } catch (Throwable error) {
            throw new QueryHandlerExecutionError(error);
        }
    }
}
