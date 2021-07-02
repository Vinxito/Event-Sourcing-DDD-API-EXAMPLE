package com.dummy.vinxi.printer.device.application.change_status;

import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.DeviceRepository;
import com.dummy.vinxi.printer.device.application.change_status.event.StatusChanged;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

import java.util.Optional;

@Service
public final class DeviceProjectionStatusChanger implements StatusChanged {

    private final DeviceRepository repository;

    public DeviceProjectionStatusChanger(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void change(String id, String status) {
        Optional<Device> probablyProjection = repository.search(id);

        if (probablyProjection.isPresent()) {
            Device projection = probablyProjection.get();
//            projection.setStatus(status);
            repository.update(projection);
        }
    }
}
