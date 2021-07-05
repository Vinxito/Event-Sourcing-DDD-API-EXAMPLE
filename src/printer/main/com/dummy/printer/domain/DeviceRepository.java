package com.dummy.printer.domain;

import com.dummy.shared.domain.criteria.Criteria;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository {

    Optional<Device> search(String id);

    List<Device> matching(Criteria criteria);

    void save(Device projection);

    void delete(Device projection);

    void update(Device projection);
}
