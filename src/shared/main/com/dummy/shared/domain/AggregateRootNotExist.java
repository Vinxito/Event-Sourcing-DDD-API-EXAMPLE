package com.dummy.shared.domain;

public class AggregateRootNotExist extends DomainError {
    public AggregateRootNotExist(String id) {
        super("aggregate_root_not_exist", String.format("The aggregate root with id: %s doesn't exist", id));
    }
}