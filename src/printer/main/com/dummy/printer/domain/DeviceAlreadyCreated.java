package com.dummy.printer.domain;

import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.shared.domain.DomainError;

public final class DeviceAlreadyCreated extends DomainError {
    public DeviceAlreadyCreated(DeviceId id) {
        super("device_already_created", String.format("The device with id: %s shouldn't already exists to be created", id.value()));
    }
}
