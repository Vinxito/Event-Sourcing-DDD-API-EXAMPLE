package com.dummy.printer.application.remove;

import com.dummy.shared.domain.bus.command.Command;

public final class RemoveDeviceCommand implements Command {

    private final String id;

    public RemoveDeviceCommand(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

}
