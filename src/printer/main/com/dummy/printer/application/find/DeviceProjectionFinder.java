package com.dummy.printer.application.find;

import com.dummy.printer.domain.DeviceRepository;
import com.dummy.printer.application.DeviceQueryResponse;
import com.dummy.shared.domain.ProjectionNotExist;
import com.dummy.shared.infrastructure.spring.Service;

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
