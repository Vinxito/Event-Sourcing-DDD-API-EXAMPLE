package com.dummy.printer.application.change_status;

import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceStatus;
import com.dummy.shared.domain.EventStoreConsumer;
import com.dummy.shared.domain.bus.event.EventBus;
import com.dummy.shared.infrastructure.spring.Service;

@Service
public class DeviceStatusChanger {

    private final EventBus eventBus;
    private final EventStoreConsumer consumer;

    public DeviceStatusChanger(EventBus eventBus, EventStoreConsumer consumer) {
        this.eventBus = eventBus;
        this.consumer = consumer;
    }

    public void change(DeviceId id, DeviceStatus status) {
        Device device = Device.changeStatus(consumer, id, status);
        eventBus.publish(device.pullDomainEvents());
    }
}
