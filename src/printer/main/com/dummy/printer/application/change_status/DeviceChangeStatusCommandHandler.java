package com.dummy.printer.application.change_status;

import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceStatus;
import com.dummy.shared.domain.bus.command.CommandHandler;
import com.dummy.shared.infrastructure.spring.Service;

@Service
public final class DeviceChangeStatusCommandHandler implements CommandHandler<ChangeStatusDeviceCommand> {

    private final DeviceStatusChanger statusChanger;

    public DeviceChangeStatusCommandHandler(DeviceStatusChanger statusChanger) {
        this.statusChanger = statusChanger;
    }

    @Override
    public void handle(ChangeStatusDeviceCommand command) {
        DeviceId id = new DeviceId(command.id());
        DeviceStatus status = new DeviceStatus(command.status());
        statusChanger.change(id, status);
    }
}
