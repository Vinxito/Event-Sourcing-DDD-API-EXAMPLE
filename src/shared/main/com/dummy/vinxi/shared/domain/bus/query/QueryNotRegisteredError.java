package com.dummy.vinxi.shared.domain.bus.query;

public final class QueryNotRegisteredError extends Exception {

    private static final long serialVersionUID = -3656196260258320970L;

    public QueryNotRegisteredError(Class<? extends Query> query) {
        super(String.format("The query %s hasn't a query handler associated", query.toString()));
    }
}
