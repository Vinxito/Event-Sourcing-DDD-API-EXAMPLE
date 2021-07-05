package com.dummy.printer.application.create;

import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceName;
import com.dummy.printer.domain.value_object.DeviceStatus;
import com.dummy.shared.domain.bus.command.CommandHandler;
import com.dummy.shared.infrastructure.spring.Service;

@Service
public final class CreateDeviceCommandHandler implements CommandHandler<CreateDeviceCommand> {

    private final DeviceCreator creator;

    public CreateDeviceCommandHandler(DeviceCreator creator) {
        this.creator = creator;
    }

    @Override
    public void handle(CreateDeviceCommand command) {
        DeviceId id = new DeviceId(command.id());
        DeviceName name = new DeviceName(command.name());
        DeviceStatus status = new DeviceStatus(command.status());
        creator.create(id, name, status);
    }
}
