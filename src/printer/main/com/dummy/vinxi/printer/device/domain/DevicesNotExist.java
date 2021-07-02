package com.dummy.vinxi.printer.device.domain;

import com.dummy.vinxi.shared.domain.DomainError;

public final class DevicesNotExist extends DomainError {
    public DevicesNotExist(String id) {
        super("device_not_exist", String.format("The device with id: %s doesn't exist", id));
    }
}
