package com.dummy.printer.application.remove.event;

import com.dummy.shared.domain.DomainEventListener;
import com.dummy.shared.domain.bus.event.DomainEventSubscriber;
import com.dummy.shared.infrastructure.spring.Service;

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
