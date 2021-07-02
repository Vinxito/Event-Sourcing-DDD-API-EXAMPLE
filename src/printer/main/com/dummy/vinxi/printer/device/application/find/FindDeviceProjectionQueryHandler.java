package com.dummy.vinxi.printer.device.application.find;

import com.dummy.vinxi.printer.device.application.DeviceQueryResponse;
import com.dummy.vinxi.shared.domain.ProjectionNotExist;
import com.dummy.vinxi.shared.domain.bus.query.QueryHandler;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
public final class FindDeviceProjectionQueryHandler implements QueryHandler<FindDeviceQuery, DeviceQueryResponse> {

    private final DeviceProjectionFinder finder;

    public FindDeviceProjectionQueryHandler(DeviceProjectionFinder finder) {
        this.finder = finder;
    }

    @Override
    public DeviceQueryResponse handle(FindDeviceQuery query) throws ProjectionNotExist {
        return finder.find(query.id());
    }
}
