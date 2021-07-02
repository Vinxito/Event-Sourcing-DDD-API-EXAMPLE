package com.dummy.vinxi.printer.device.application.change_status.event;

import com.dummy.vinxi.shared.domain.DomainEventListener;
import com.dummy.vinxi.shared.domain.bus.event.DomainEventSubscriber;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
@DomainEventSubscriber({DeviceStatusChangedDomainEvent.class})
public final class ChangeStatusDeviceProjectionOnDeviceStatusChanged {

    private final StatusChanged projectionChangerStatus;

    public ChangeStatusDeviceProjectionOnDeviceStatusChanged(StatusChanged projectionChangerStatus) {
        this.projectionChangerStatus = projectionChangerStatus;
    }

    @DomainEventListener
    public void on(DeviceStatusChangedDomainEvent aggregateStatusChangedDomainEvent) {
        projectionChangerStatus.change(
                aggregateStatusChangedDomainEvent.aggregateId(),
                aggregateStatusChangedDomainEvent.status());
    }
}
