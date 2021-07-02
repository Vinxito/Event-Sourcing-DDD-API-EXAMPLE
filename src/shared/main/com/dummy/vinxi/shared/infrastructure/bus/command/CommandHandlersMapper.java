package com.dummy.vinxi.shared.infrastructure.bus.command;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import com.dummy.vinxi.shared.domain.bus.command.Command;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandler;
import com.dummy.vinxi.shared.domain.bus.command.CommandNotRegisteredError;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;

@Service
public final class CommandHandlersMapper {

    @SuppressWarnings("rawtypes")
    HashMap<Class<? extends Command>, Class<? extends CommandHandler>> indexedCommandHandlers;

    @SuppressWarnings("rawtypes")
    public CommandHandlersMapper() {

        Reflections reflections = new Reflections("com.dummy.vinxi");

        Set<Class<? extends CommandHandler>> classes = reflections.getSubTypesOf(CommandHandler.class);

        indexedCommandHandlers = formatHandlers(classes);
    }

    @SuppressWarnings("rawtypes")
    public Class<? extends CommandHandler> search(Class<? extends Command> commandClass)
            throws CommandNotRegisteredError {

        Class<? extends CommandHandler> commandHandlerClass = indexedCommandHandlers.get(commandClass);

        if (null == commandHandlerClass) {
            throw new CommandNotRegisteredError(commandClass);
        }

        return commandHandlerClass;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private HashMap<Class<? extends Command>, Class<? extends CommandHandler>> formatHandlers(
            Set<Class<? extends CommandHandler>> commandHandlers) {

        HashMap<Class<? extends Command>, Class<? extends CommandHandler>> handlers = new HashMap<>();

        for (Class<? extends CommandHandler> handler : commandHandlers) {
            ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];

            Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];

            handlers.put(commandClass, handler);
        }

        return handlers;
    }
}
