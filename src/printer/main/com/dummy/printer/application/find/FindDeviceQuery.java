package com.dummy.printer.application.find;

import com.dummy.shared.domain.bus.query.Query;

public final class FindDeviceQuery implements Query {

    private final String id;

    public FindDeviceQuery(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
