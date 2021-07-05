package com.dummy.printer.application.create.event;

import com.dummy.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public final class DeviceCreatedDomainEvent extends DomainEvent {

    private final String name;
    private final String status;

    public DeviceCreatedDomainEvent() {
        super(null);
        this.name = null;
        this.status = null;
    }

    public DeviceCreatedDomainEvent(String aggregateId, String name, String status) {
        super(aggregateId);
        this.name = name;
        this.status = status;
    }

    public DeviceCreatedDomainEvent(
            String aggregateId,
            String eventId,
            String occurredOn,
            String name,
            String status,
            String version) {
        super(aggregateId, eventId, occurredOn, version);
        this.name = name;
        this.status = status;
    }

    @Override
    public String eventName() {
        return "device.created";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("name", name);
            put("status", status);
        }};
    }

    @Override
    public DeviceCreatedDomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn,
            String version) {
        return new DeviceCreatedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                (String) body.get("name"),
                (String) body.get("status"),
                version);
    }

    public String name() {
        return name;
    }

    public String status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceCreatedDomainEvent that = (DeviceCreatedDomainEvent) o;
        return name.equals(that.name) &&
                status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }
}
