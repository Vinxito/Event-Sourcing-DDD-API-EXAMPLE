package com.dummy.vinxi.printer.device.infrastruture.persistence;

import com.dummy.vinxi.printer.device.domain.Device;
import com.dummy.vinxi.printer.device.domain.DeviceRepository;
import com.dummy.vinxi.shared.domain.criteria.Criteria;
import com.dummy.vinxi.shared.infrastructure.persistence.elasticsearch.ElasticsearchClient;
import com.dummy.vinxi.shared.infrastructure.persistence.elasticsearch.ElasticsearchRepository;
import com.dummy.vinxi.shared.infrastructure.spring.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@ConditionalOnProperty(value = "elk.enable", havingValue = "true", matchIfMissing = true)
public final class ElasticsearchDeviceRepository extends ElasticsearchRepository<Device>
    implements DeviceRepository {

  public ElasticsearchDeviceRepository(ElasticsearchClient client) {
    super(client);
  }

  @Override
  public Optional<Device> search(String id) {
    return searchById(Device::fromPrimitives, id);
  }

  @Override
  public List<Device> matching(Criteria criteria) {
    return searchByCriteria(criteria, Device::fromPrimitives);
  }

  @Override
  public void save(Device projection) {
    persist(projection.toPrimitives());
  }

  @Override
  public void delete(Device projection) {
    remove(projection.id().value());
  }

  @Override
  public void update(Device projection) {
    actualize(projection.id().value(), projection.toPrimitives());
  }

  @Override
  protected String moduleName() {
    return "device";
  }
}
