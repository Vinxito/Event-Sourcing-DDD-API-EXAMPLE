package com.dummy.vinxi.shared.domain;

public final class ProjectionNotExist extends DomainError {
    public ProjectionNotExist(String id) {
        super("projection_not_exist", String.format("The projection %s doesn't exist", id));
    }
}
