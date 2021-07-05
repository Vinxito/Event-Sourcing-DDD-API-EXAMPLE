package com.dummy.printer.application.create;

import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceName;
import com.dummy.printer.domain.value_object.DeviceStatus;
import com.dummy.shared.domain.EventStoreConsumer;
import com.dummy.shared.domain.bus.event.EventBus;
import com.dummy.shared.infrastructure.spring.Service;

@Service
public final class DeviceCreator {

    private final EventBus eventBus;
    private final EventStoreConsumer consumer;

    public DeviceCreator(EventBus eventBus, EventStoreConsumer consumer) {
        this.eventBus = eventBus;
        this.consumer = consumer;
    }

    public void create(DeviceId id, DeviceName name, DeviceStatus status) {
        Device device = Device.create(consumer, id, name, status);
        eventBus.publish(device.pullDomainEvents());
    }

}
