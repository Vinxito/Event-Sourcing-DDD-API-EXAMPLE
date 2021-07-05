package com.dummy.printer.application.create;

import com.dummy.shared.domain.bus.command.Command;

public final class CreateDeviceCommand implements Command {

    private final String id;
    private final String name;
    private final String status;

    public CreateDeviceCommand(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String status() {
        return status;
    }
}
