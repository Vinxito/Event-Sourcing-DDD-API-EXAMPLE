package com.dummy.printer.domain;

import com.dummy.shared.domain.DomainError;

public final class DevicesNotExist extends DomainError {
    public DevicesNotExist(String id) {
        super("device_not_exist", String.format("The device with id: %s doesn't exist", id));
    }
}
