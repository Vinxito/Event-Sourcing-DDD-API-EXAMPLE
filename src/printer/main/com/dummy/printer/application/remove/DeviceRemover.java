package com.dummy.printer.application.remove;

import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.shared.domain.EventStoreConsumer;
import com.dummy.shared.domain.bus.event.EventBus;
import com.dummy.shared.infrastructure.spring.Service;

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
