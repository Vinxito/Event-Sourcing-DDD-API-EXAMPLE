package com.dummy.vinxi.printer.device.application.create.event;

public interface DeviceCreated {
    void create(String aggregateId, String name, String status);
}
