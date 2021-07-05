package com.dummy.printer.domain.domain;

import java.util.UUID;

public final class UuidMother {
    public static String random() {
        return UUID.randomUUID().toString();
    }
}
