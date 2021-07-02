package com.dummy.vinxi.printer.device.application.remove.event;

import com.dummy.vinxi.shared.domain.DomainEventListener;
import com.dummy.vinxi.shared.domain.bus.event.DomainEventSubscriber;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
@DomainEventSubscriber(DeviceRemovedDomainEvent.class)
public final class RemoveDeviceProjectionOnDeviceRemoved {

    private final DeviceRemoved projectionRemover;

    public RemoveDeviceProjectionOnDeviceRemoved(DeviceRemoved projectionRemover) {
        this.projectionRemover = projectionRemover;
    }

    @DomainEventListener
    public void on(DeviceRemovedDomainEvent event) {
        projectionRemover.delete(event.aggregateId());
    }
}
