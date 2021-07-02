package com.dummy.vinxi.shared.domain.bus.query;

public final class QueryHandlerExecutionError extends RuntimeException {

    private static final long serialVersionUID = 4819261941820395767L;

    public QueryHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
