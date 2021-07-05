package com.dummy.shared.infrastructure.spring;

import com.dummy.shared.domain.bus.command.Command;
import com.dummy.shared.domain.bus.command.CommandHandlerExecutionError;
import com.dummy.shared.domain.bus.command.CommandBus;

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
