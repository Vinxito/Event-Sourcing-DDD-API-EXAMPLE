package com.dummy.vinxi.printer.device.application.remove;

import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.shared.domain.EventStoreConsumer;
import com.dummy.vinxi.shared.domain.bus.event.EventBus;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
public final class DeviceRemover {

    private final EventBus eventBus;
    private final EventStoreConsumer consumer;

    public DeviceRemover(EventBus eventBus, EventStoreConsumer consumer) {
        this.eventBus = eventBus;
        this.consumer = consumer;
    }

    public void remove(DeviceId id) {
        Device device = Device.delete(consumer, id);
        eventBus.publish(device.pullDomainEvents());
    }
}
