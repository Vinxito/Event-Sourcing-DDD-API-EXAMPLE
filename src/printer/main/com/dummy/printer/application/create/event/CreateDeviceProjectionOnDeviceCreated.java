package com.dummy.printer.application.create.event;

import com.dummy.shared.domain.DomainEventListener;
import com.dummy.shared.domain.bus.event.DomainEventSubscriber;
import com.dummy.shared.infrastructure.spring.Service;

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
