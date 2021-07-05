package com.dummy.printer.application.remove;

import com.dummy.printer.application.remove.event.DeviceRemoved;
import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.DeviceRepository;
import com.dummy.shared.infrastructure.spring.Service;

import java.util.Optional;

@Service
public final class DeviceProjectionRemover implements DeviceRemoved {

    private final DeviceRepository repository;

    public DeviceProjectionRemover(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(String id) {
        Optional<Device> probablyProjection = repository.search(id);

        if (probablyProjection.isPresent()) {
            Device projection = probablyProjection.get();
            repository.delete(projection);
        }
    }
}
