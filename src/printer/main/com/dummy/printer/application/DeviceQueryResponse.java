package com.dummy.printer.application;

import com.dummy.shared.domain.bus.query.Response;

public final class DeviceQueryResponse implements Response {

    private final String id;
    private final String name;
    private final String status;

    public DeviceQueryResponse(String id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String status() {
        return status;
    }
}
