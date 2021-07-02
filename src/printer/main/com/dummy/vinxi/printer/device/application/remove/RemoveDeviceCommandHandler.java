package com.dummy.vinxi.printer.device.application.remove;

import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandler;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
public final class RemoveDeviceCommandHandler implements CommandHandler<RemoveDeviceCommand> {

    private final DeviceRemover remover;

    public RemoveDeviceCommandHandler(DeviceRemover remover) {
        this.remover = remover;
    }

    @Override
    public void handle(RemoveDeviceCommand command) {
        DeviceId id = new DeviceId(command.id());
        remover.remove(id);
    }
}
