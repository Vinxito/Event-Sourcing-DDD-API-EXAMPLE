package com.dummy.vinxi.shared.infrastructure;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import com.dummy.vinxi.shared.domain.UuidGenerator;

import java.util.UUID;

@Service
public final class JavaUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
