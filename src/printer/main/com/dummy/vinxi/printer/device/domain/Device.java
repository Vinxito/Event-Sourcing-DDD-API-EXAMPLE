package com.dummy.vinxi.printer.device.domain;

import com.dummy.vinxi.printer.device.application.change_status.event.DeviceStatusChangedDomainEvent;
import com.dummy.vinxi.printer.device.application.create.event.DeviceCreatedDomainEvent;
import com.dummy.vinxi.printer.device.application.remove.event.DeviceRemovedDomainEvent;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceId;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceName;
import com.dummy.vinxi.printer.device.domain.value_object.DeviceStatus;
import com.dummy.vinxi.shared.domain.AggregateRoot;
import com.dummy.vinxi.shared.domain.EventStoreConsumer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Device extends AggregateRoot {

  private DeviceId id;
  private DeviceName name;
  private DeviceStatus status;

  public Device(DeviceId id, DeviceName name, DeviceStatus status) {
    super();
    this.id = id;
    this.name = name;
    this.status = status;
  }

  public Device(EventStoreConsumer consumer, DeviceId id, DeviceName name, DeviceStatus status) {
    super(consumer, id.value());
    alreadyExists(id);
    this.id = id;
    this.name = name;
    this.status = status;
  }

  public Device(EventStoreConsumer consumer, DeviceId id, DeviceStatus status) {
    super(consumer, id.value());
    isValid(id);
    this.id = id;
    this.status = status;
  }

  public static Device fromPrimitives(Map<String, Object> plainData) {
    return new Device(
        new DeviceId((String) plainData.get("id")),
        new DeviceName((String) plainData.get("name")),
        new DeviceStatus((String) plainData.get("status")));
  }

  public HashMap<String, Serializable> toPrimitives() {
    return new HashMap<String, Serializable>() {
      {
        put("id", id);
        put("name", name);
        put("status", status);
      }
    };
  }

  public static Device create(DeviceId id, DeviceName name, DeviceStatus status) {
    Device device = new Device(id, name, status);
    device.record(new DeviceCreatedDomainEvent(id.value(), name.value(), status.value()));
    return device;
  }

  public static Device create(
      EventStoreConsumer consumer, DeviceId id, DeviceName name, DeviceStatus status) {
    Device device = new Device(consumer, id, name, status);
    device.record(new DeviceCreatedDomainEvent(id.value(), name.value(), status.value()));
    return device;
  }

  public static Device delete(EventStoreConsumer consumer, DeviceId id) {
    Device device = new Device(consumer, id, new DeviceStatus());
    device.record(new DeviceRemovedDomainEvent(id.value()));
    return device;
  }

  public static Device changeStatus(EventStoreConsumer consumer, DeviceId id, DeviceStatus status) {
    Device device = new Device(consumer, id, status);
    device.record(new DeviceStatusChangedDomainEvent(id.value(), status.value()));
    return device;
  }

  private void alreadyExists(DeviceId id) {
    if (Objects.nonNull(this.id)
        && Objects.nonNull(this.id.value())
        && this.id.value().equals(id.value())) throw new DeviceAlreadyCreated(id);
  }

  private void isValid(DeviceId id) {
    if (Objects.isNull(this.id) || Objects.isNull(this.id.value()))
      throw new DevicesNotExist(id.value());
  }

  public void apply(DeviceCreatedDomainEvent event) {
    id = new DeviceId(event.aggregateId());
    name = new DeviceName(event.name());
    status = new DeviceStatus(event.status());
  }

  public void apply(DeviceRemovedDomainEvent event) {
    id = new DeviceId();
    name = new DeviceName();
    status = new DeviceStatus();
  }

  public void apply(DeviceStatusChangedDomainEvent event) {
    status = new DeviceStatus(event.status());
  }

  public DeviceId id() {
    return id;
  }

  public DeviceName name() {
    return name;
  }

  public DeviceStatus status() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Device device = (Device) o;
    return id.equals(device.id) && name.equals(device.name) && status.equals(device.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, status);
  }
}
