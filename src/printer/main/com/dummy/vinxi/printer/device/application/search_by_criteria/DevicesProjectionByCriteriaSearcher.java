package com.dummy.vinxi.printer.device.application.search_by_criteria;

import com.dummy.vinxi.printer.device.application.DeviceQueryResponse;
import com.dummy.vinxi.printer.device.application.DevicesQueryResponse;
import com.dummy.vinxi.printer.device.domain.DeviceRepository;
import com.dummy.vinxi.shared.domain.criteria.Criteria;
import com.dummy.vinxi.shared.domain.criteria.Filters;
import com.dummy.vinxi.shared.domain.criteria.Order;
import com.dummy.vinxi.shared.infrastructure.spring.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class DevicesProjectionByCriteriaSearcher {

  private final DeviceRepository repository;

  public DevicesProjectionByCriteriaSearcher(DeviceRepository repository) {
    this.repository = repository;
  }

  public DevicesQueryResponse search(
          Filters filters, Order order, Optional<Integer> limit, Optional<Integer> offset) {

    Criteria criteria = new Criteria(filters, order, limit, offset);

    return new DevicesQueryResponse(
        repository.matching(criteria).stream()
            .map(
                projection ->
                    new DeviceQueryResponse(
                        projection.id().value(),
                        projection.name().value(),
                        projection.status().value()))
            .collect(Collectors.toList()));
  }
}
