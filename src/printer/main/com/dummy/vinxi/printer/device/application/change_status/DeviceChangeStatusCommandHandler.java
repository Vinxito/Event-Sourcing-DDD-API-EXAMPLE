package com.dummy.vinxi.printer.device.application.change_status;

import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceStatus;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandler;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

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
