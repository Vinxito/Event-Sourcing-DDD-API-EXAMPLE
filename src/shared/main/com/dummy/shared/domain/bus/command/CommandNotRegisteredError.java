package com.dummy.shared.domain.bus.command;

public final class CommandNotRegisteredError extends Exception {

    private static final long serialVersionUID = 2829209838399328797L;

    public CommandNotRegisteredError(Class<? extends Command> command) {
        super(String.format("The command %s hasn't a command handler associated", command.toString()));
    }
}
