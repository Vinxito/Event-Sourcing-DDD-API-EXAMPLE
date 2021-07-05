package com.dummy.printer.domain.value_object;

import com.dummy.shared.domain.value_object.TypeValueObject;

public class DeviceStatus extends TypeValueObject<String> {

    public DeviceStatus(String value) {
        super(value);
    }

    public DeviceStatus() {
        super("");
    }
}
