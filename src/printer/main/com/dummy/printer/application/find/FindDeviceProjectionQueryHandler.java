package com.dummy.printer.application.find;

import com.dummy.printer.application.DeviceQueryResponse;
import com.dummy.shared.domain.ProjectionNotExist;
import com.dummy.shared.domain.bus.query.QueryHandler;
import com.dummy.shared.infrastructure.spring.Service;

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
