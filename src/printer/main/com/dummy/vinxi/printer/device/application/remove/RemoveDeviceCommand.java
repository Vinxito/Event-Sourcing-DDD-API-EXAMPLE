package com.dummy.vinxi.printer.device.application.remove;

import com.dummy.vinxi.shared.domain.bus.command.Command;

public final class RemoveDeviceCommand implements Command {

    private final String id;

    public RemoveDeviceCommand(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

}
