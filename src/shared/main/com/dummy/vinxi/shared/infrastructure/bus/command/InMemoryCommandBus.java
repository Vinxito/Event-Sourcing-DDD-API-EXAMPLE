package com.dummy.vinxi.shared.infrastructure.bus.command;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import com.dummy.vinxi.shared.domain.bus.command.Command;
import com.dummy.vinxi.shared.domain.bus.command.CommandBus;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandler;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.context.ApplicationContext;

@Service
public final class InMemoryCommandBus implements CommandBus {

  private final CommandHandlersMapper information;
  private final ApplicationContext context;

  public InMemoryCommandBus(CommandHandlersMapper information, ApplicationContext context) {
    this.information = information;
    this.context = context;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void dispatch(Command command) throws CommandHandlerExecutionError {
    try {
      Class<? extends CommandHandler> commandHandlerClass = information.search(command.getClass());

      CommandHandler handler = context.getBean(commandHandlerClass);

      handler.handle(command);
    } catch (Throwable error) {
      throw new CommandHandlerExecutionError(error);
    }
  }
}
