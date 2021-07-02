package com.dummy.vinxi.printer.device.domain.value_object;

import com.dummy.vinxi.shared.domain.value_object.TypeValueObject;

public class DeviceStatus extends TypeValueObject<String> {

    public DeviceStatus(String value) {
        super(value);
    }

    public DeviceStatus() {
        super("");
    }
}
