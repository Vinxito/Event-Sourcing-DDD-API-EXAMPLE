package com.dummy.printer.application.change_status.event;

public interface StatusChanged {
    void change(String aggregateId, String status);
}
