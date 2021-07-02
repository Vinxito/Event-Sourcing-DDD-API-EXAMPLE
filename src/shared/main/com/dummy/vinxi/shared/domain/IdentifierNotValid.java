package com.dummy.vinxi.shared.domain;

public final class IdentifierNotValid extends DomainError {
    public IdentifierNotValid(String id) {
        super("invalid_uuid", String.format("Error on validating UUID '%s', should be a valid format", id));
    }
}
