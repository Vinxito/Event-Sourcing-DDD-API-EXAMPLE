package com.dummy.vinxi.printer.device.application.find;

import com.dummy.vinxi.printer.device.application.DeviceQueryResponse;
import com.dummy.vinxi.printer.device.domain.DeviceRepository;
import com.dummy.vinxi.shared.domain.ProjectionNotExist;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

@Service
public final class DeviceProjectionFinder {

  private final DeviceRepository repository;

  public DeviceProjectionFinder(DeviceRepository repository) {
    this.repository = repository;
  }

  public DeviceQueryResponse find(String id) throws ProjectionNotExist {
    return repository
        .search(id)
        .map(
            projection ->
                new DeviceQueryResponse(
                    projection.id().value(),
                    projection.name().value(),
                    projection.status().value()))
        .orElseThrow(() -> new ProjectionNotExist(id));
  }
}
