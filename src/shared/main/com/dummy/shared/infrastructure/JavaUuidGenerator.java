package com.dummy.shared.infrastructure;

import com.dummy.shared.domain.UuidGenerator;
import com.dummy.shared.infrastructure.spring.Service;

import java.util.UUID;

@Service
public final class JavaUuidGenerator implements UuidGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
