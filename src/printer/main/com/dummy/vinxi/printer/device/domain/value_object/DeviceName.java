package com.dummy.vinxi.printer.device.domain.value_object;

import com.dummy.vinxi.shared.domain.value_object.TypeValueObject;

public class DeviceName extends TypeValueObject<String> {

    public DeviceName(String value) {
        super(value);
    }

    public DeviceName() {
        super("");
    }
}