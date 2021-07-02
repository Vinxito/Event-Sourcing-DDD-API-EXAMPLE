package com.dummy.vinxi.printer.device.application.create.event;

import com.dummy.vinxi.shared.domain.DomainEventListener;
import com.dummy.vinxi.shared.domain.bus.event.DomainEventSubscriber;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
@DomainEventSubscriber({DeviceCreatedDomainEvent.class})
public final class CreateDeviceProjectionOnDeviceCreated {

    private final DeviceCreated projectionCreator;

    public CreateDeviceProjectionOnDeviceCreated(DeviceCreated projectionCreator) {
        this.projectionCreator = projectionCreator;
    }

    @DomainEventListener
    public void on(DeviceCreatedDomainEvent aggregateCreatedDomainEvent) {
        projectionCreator.create(
                aggregateCreatedDomainEvent.aggregateId(),
                aggregateCreatedDomainEvent.name(),
                aggregateCreatedDomainEvent.status());
    }
}
