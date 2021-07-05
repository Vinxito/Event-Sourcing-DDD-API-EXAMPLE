package com.dummy.printer.application.create;

import com.dummy.printer.application.create.event.DeviceCreated;
import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.DeviceRepository;
import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceName;
import com.dummy.printer.domain.value_object.DeviceStatus;
import com.dummy.shared.infrastructure.spring.Service;

import java.util.Optional;

@Service
public final class DeviceProjectionCreator implements DeviceCreated {

  private final DeviceRepository repository;

  public DeviceProjectionCreator(DeviceRepository repository) {
    this.repository = repository;
  }

  @Override
  public void create(String id, String name, String status) {
    Optional<Device> probablyProjection = repository.search(id);

    if (!probablyProjection.isPresent())
      repository.save(new Device(new DeviceId(id), new DeviceName(name), new DeviceStatus(status)));
  }
}
