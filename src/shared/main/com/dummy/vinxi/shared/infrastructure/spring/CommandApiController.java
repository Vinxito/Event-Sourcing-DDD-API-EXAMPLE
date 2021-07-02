package com.dummy.vinxi.shared.infrastructure.spring;

import com.dummy.vinxi.shared.domain.bus.command.Command;
import com.dummy.vinxi.shared.domain.bus.command.CommandBus;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandlerExecutionError;

public abstract class CommandApiController extends ApiController {

    private final CommandBus commandBus;

    public CommandApiController(CommandBus commandBus) {
        super();
        this.commandBus = commandBus;
    }

    protected void dispatch(Command command) throws CommandHandlerExecutionError {
        commandBus.dispatch(command);
    }
}
