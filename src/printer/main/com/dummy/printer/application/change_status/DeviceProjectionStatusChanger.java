package com.dummy.printer.application.change_status;

import com.dummy.printer.application.change_status.event.StatusChanged;
import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.DeviceRepository;
import com.dummy.shared.infrastructure.spring.Service;

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
