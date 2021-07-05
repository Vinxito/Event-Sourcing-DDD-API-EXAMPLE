package com.dummy.printer.application;

import com.dummy.shared.domain.bus.query.Response;

import java.util.List;

public final class DevicesQueryResponse implements Response {

    private final List<DeviceQueryResponse> projections;

    public DevicesQueryResponse(List<DeviceQueryResponse> projections) {
        this.projections = projections;
    }

    public List<DeviceQueryResponse> projections() {
        return projections;
    }
}
