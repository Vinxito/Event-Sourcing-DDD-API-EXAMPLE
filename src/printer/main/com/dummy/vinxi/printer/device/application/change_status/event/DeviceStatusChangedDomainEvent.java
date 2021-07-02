package com.dummy.vinxi.printer.device.application.change_status.event;

import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public class DeviceStatusChangedDomainEvent extends DomainEvent {

    private final String status;

    public DeviceStatusChangedDomainEvent() {
        super(null);
        this.status = null;
    }

    public DeviceStatusChangedDomainEvent(String aggregateId, String aggregateStatus) {
        super(aggregateId);
        status = aggregateStatus;
    }

    public DeviceStatusChangedDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String status,
            String version) {
        super(aggregateId, eventId, occurredOn, version);

        this.status = status;
    }

    @Override
    public String eventName() {
        return "device.status_changed";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("status", status);
        }};
    }

    @Override
    public DeviceStatusChangedDomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn,
            String version) {
        return new DeviceStatusChangedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("status"),
                version);
    }

    public String status() {
        return this.status;
    }
}
