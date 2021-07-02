package com.dummy.vinxi.printer.device.application;

import com.dummy.vinxi.shared.domain.bus.query.Response;

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
