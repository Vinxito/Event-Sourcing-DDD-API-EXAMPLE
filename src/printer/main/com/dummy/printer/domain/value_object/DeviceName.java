package com.dummy.printer.domain.value_object;

import com.dummy.shared.domain.value_object.TypeValueObject;

public class DeviceName extends TypeValueObject<String> {

    public DeviceName(String value) {
        super(value);
    }

    public DeviceName() {
        super("");
    }
}