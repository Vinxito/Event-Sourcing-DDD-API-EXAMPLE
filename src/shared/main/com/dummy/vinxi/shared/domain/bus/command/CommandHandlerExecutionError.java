package com.dummy.vinxi.shared.domain.bus.command;

public final class CommandHandlerExecutionError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CommandHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
