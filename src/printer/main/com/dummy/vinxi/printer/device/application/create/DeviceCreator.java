package com.dummy.vinxi.printer.device.application.create;

import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceName;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceStatus;
import com.dummy.vinxi.shared.domain.EventStoreConsumer;
import com.dummy.vinxi.shared.domain.bus.event.EventBus;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

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
