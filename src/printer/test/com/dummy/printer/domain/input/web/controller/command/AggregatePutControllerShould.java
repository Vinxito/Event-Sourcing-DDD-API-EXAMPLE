package com.dummy.printer.domain.input.web.controller.command;

import com.dummy.printer.domain.input.WebAdapterTestCase;
import org.junit.Test;

public final class AggregatePutControllerShould extends WebAdapterTestCase {

    @Test
    public void create_a_valid_non_existing_aggregate() throws Exception {
        assertRequestWithBody(
                "PUT",
                "/aggregates/create/1aab45ba-3c7a-4344-8936-78466eca77fa",
                "{\"name\": \"Beatriz\", \"status\": \"Bailando bajo la lluvia\"}",
                201
        );
    }

}
