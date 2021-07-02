package com.dummy.vinxi.printer.device.application.change_status.event;

public interface StatusChanged {
    void change(String aggregateId, String status);
}
