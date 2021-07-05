package com.dummy.printer.domain.infrastructure;

import com.dummy.shared.domain.UuidGenerator;
import com.dummy.shared.domain.bus.event.DomainEvent;
import com.dummy.shared.domain.bus.event.EventBus;
import com.dummy.shared.domain.bus.query.Query;
import com.dummy.shared.domain.bus.query.QueryBus;
import com.dummy.shared.domain.bus.query.Response;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public abstract class UnitTestCase {

    protected EventBus eventBus;
    protected QueryBus queryBus;
    protected UuidGenerator uuidGenerator;

    @BeforeEach
    protected void setUp() {
        eventBus = mock(EventBus.class);
        queryBus = mock(QueryBus.class);
        uuidGenerator = mock(UuidGenerator.class);
    }

    public void shouldHavePublished(List<DomainEvent> domainEvents) {
        verify(eventBus, atLeastOnce()).publish(domainEvents);
    }

    public void shouldHavePublished(DomainEvent domainEvent) {
        shouldHavePublished(Collections.singletonList(domainEvent));
    }

    public void shouldGenerateUuid(String uuid) {
        when(uuidGenerator.generate()).thenReturn(uuid);
    }

    public void shouldGenerateUuids(String uuid, String... others) {
        when(uuidGenerator.generate()).thenReturn(uuid, others);
    }

    public void shouldAsk(Query query, Response response) {
        when(queryBus.ask(query)).thenReturn(response);
    }
}
