package com.dummy.vinxi.printer.device.application.change_status;

import com.dummy.vinxi.shared.domain.bus.command.Command;

public final class ChangeStatusDeviceCommand implements Command {

    private final String id;
    private final String status;

    public ChangeStatusDeviceCommand(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String id() {
        return id;
    }

    public String status() {
        return status;
    }
}