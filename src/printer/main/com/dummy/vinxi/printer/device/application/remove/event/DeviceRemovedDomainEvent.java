package com.dummy.vinxi.printer.device.application.remove.event;

import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;

public final class DeviceRemovedDomainEvent extends DomainEvent {

    public DeviceRemovedDomainEvent() {
        super(null);
    }

    public DeviceRemovedDomainEvent(String aggregateId) {
        super(aggregateId);
    }

    public DeviceRemovedDomainEvent(String aggregateId, String eventId, String occurredOn, String version) {
        super(aggregateId, eventId, occurredOn, version);
    }

    @Override
    public String eventName() {
        return "device.removed";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
        }};
    }

    @Override
    public DeviceRemovedDomainEvent fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn,
            String version) {
        return new DeviceRemovedDomainEvent(
                aggregateId,
                eventId,
                occurredOn,
                version);
    }
}
