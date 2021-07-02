package com.dummy.vinxi.printer.device.application.change_status;

import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceStatus;
import com.dummy.vinxi.shared.domain.EventStoreConsumer;
import com.dummy.vinxi.shared.domain.bus.event.EventBus;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

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
