package com.dummy.vinxi.shared.infrastructure.bus.event;

import com.dummy.vinxi.shared.domain.bus.event.DomainEvent;
import com.dummy.vinxi.shared.domain.Utils;
import org.springframework.core.env.Environment;

import java.io.Serializable;
import java.util.HashMap;

public final class DomainEventJsonSerializer {

    public static String serialize(DomainEvent domainEvent, Environment environment) {

        HashMap<String, Serializable> attributes = domainEvent.toPrimitives();
        attributes.put("id", domainEvent.aggregateId());

        return Utils.jsonEncode(new HashMap<String, Serializable>() {{
            put("data", new HashMap<String, Serializable>() {{
                put("id", domainEvent.eventId());
                put("type", domainEvent.eventName());
                put("occurred_on", domainEvent.occurredOn());
                put("version", domainEvent.version());
                put("attributes", attributes);
            }});
            put("meta", domainEvent.meta());
        }});
    }
}
