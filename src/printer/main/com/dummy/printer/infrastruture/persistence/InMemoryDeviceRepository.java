package com.dummy.printer.infrastruture.persistence;

import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.DeviceRepository;
import com.dummy.shared.domain.criteria.Criteria;
import com.dummy.shared.infrastructure.spring.Service;

import java.util.*;

@Service
public final class InMemoryDeviceRepository implements DeviceRepository {

    private final HashMap<String, List<Device>> matchProjections = new HashMap<>();
    private final List<Device> projections = new ArrayList<>();

    public InMemoryDeviceRepository() {
    }

    @Override
    public Optional<Device> search(String id) {
        return projections.stream().filter(projection -> projection.id().equals(id)).findFirst();
    }

    @Override
    public List<Device> matching(Criteria criteria) {
        return matchProjections.containsKey(criteria.serialize())
                ? matchProjections.get(criteria.serialize())
                : searchMatchingAndFillCache(criteria);
    }

    private List<Device> searchMatchingAndFillCache(Criteria criteria) {
        List<Device> projections = matching(criteria);
        this.matchProjections.put(criteria.serialize(), projections);
        return projections;
    }

    @Override
    public void save(Device projection) {
        matchProjections.put(projection.id().value(), Arrays.asList(projection));
        projections.add(projection);
    }

    @Override
    public void delete(Device projection) {
        projection = null;
    }

    @Override
    public void update(Device projection) {
        List<Device> projections = new ArrayList(this.matchProjections.values());
        projections.remove(projection);
        projections.add(projection);
        this.matchProjections.put(projection.id().value(), projections);
    }
}