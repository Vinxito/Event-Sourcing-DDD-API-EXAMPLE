package com.dummy.printer.application.create.event;

public interface DeviceCreated {
    void create(String aggregateId, String name, String status);
}
