package com.dummy.shared.infrastructure.spring;

import com.dummy.shared.domain.bus.query.QueryBus;
import com.dummy.shared.domain.bus.query.Query;
import com.dummy.shared.domain.bus.query.QueryHandlerExecutionError;

public abstract class QueryApiController extends ApiController {

    private final QueryBus queryBus;

    public QueryApiController(QueryBus queryBus) {
        super();
        this.queryBus = queryBus;
    }

    protected <R> R ask(Query query) throws QueryHandlerExecutionError {
        return queryBus.ask(query);
    }
}
