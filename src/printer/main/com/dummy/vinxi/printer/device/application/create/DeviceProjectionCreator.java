package com.dummy.vinxi.printer.device.application.create;

import com.dummy.vinxi.printer.device.application.create.event.DeviceCreated;
import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.DeviceRepository;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceName;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceStatus;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

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
