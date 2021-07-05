package com.dummy.printer.application.search_by_criteria;

import com.dummy.printer.domain.DeviceRepository;
import com.dummy.printer.application.DeviceQueryResponse;
import com.dummy.printer.application.DevicesQueryResponse;
import com.dummy.shared.domain.criteria.Criteria;
import com.dummy.shared.domain.criteria.Filters;
import com.dummy.shared.domain.criteria.Order;
import com.dummy.shared.infrastructure.spring.Service;

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
