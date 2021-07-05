package com.dummy.device.controller;

import com.dummy.printer.application.search_by_criteria.SearchDevicesByCriteriaQuery;
import com.dummy.printer.application.DeviceQueryResponse;
import com.dummy.printer.application.DevicesQueryResponse;
import com.dummy.printer.application.find.FindDeviceQuery;
import com.dummy.shared.domain.bus.query.QueryBus;
import com.dummy.shared.infrastructure.spring.QueryApiController;
import com.dummy.shared.domain.DomainError;
import com.dummy.shared.domain.IdentifierNotValid;
import com.dummy.shared.domain.ProjectionNotExist;
import com.dummy.shared.domain.bus.query.QueryHandlerExecutionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/devices")
public final class DevicesGetController extends QueryApiController {

    public DevicesGetController(QueryBus queryBus) {
        super(queryBus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Serializable>> index(@PathVariable String id) throws QueryHandlerExecutionError {
        DeviceQueryResponse aggregate = ask(new FindDeviceQuery(id));

        return ResponseEntity.ok().body(new HashMap<String, Serializable>() {{
            put("id", aggregate.id());
            put("name", aggregate.name());
            put("status", aggregate.status());
        }});
    }

    @GetMapping()
    public List<HashMap<String, String>> findDevice(@RequestParam HashMap<String, Serializable> params) throws QueryHandlerExecutionError {
        DevicesQueryResponse aggregatesResponse = ask(new SearchDevicesByCriteriaQuery(
                parseFilters(params),
                Optional.ofNullable((String) params.get("order_by")),
                Optional.ofNullable((String) params.get("order")),
                Optional.ofNullable((Integer) params.get("limit")),
                Optional.ofNullable((Integer) params.get("offset")))
        );

        return aggregatesResponse.projections().stream().map(response -> new HashMap<String, String>() {{
            put("id", response.id());
            put("name", response.name());
            put("status", response.status());
        }}).collect(Collectors.toList());
    }

    private List<HashMap<String, String>> parseFilters(HashMap<String, Serializable> params) {

        int maxParams = params.size();
        List<HashMap<String, String>> filters = new ArrayList<>();

        for (int possibleFilterKey = 0; possibleFilterKey < maxParams; possibleFilterKey++) {
            if (params.containsKey(String.format("filters[%s][field]", possibleFilterKey))) {
                int key = possibleFilterKey;

                filters.add(new HashMap<String, String>() {{
                    put("field", (String) params.get(String.format("filters[%s][field]", key)));
                    put("operator", (String) params.get(String.format("filters[%s][operator]", key)));
                    put("value", (String) params.get(String.format("filters[%s][value]", key)));
                }});
            }
        }
        return filters;
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<Class<? extends DomainError>, HttpStatus>() {{
            put(ProjectionNotExist.class, HttpStatus.NOT_FOUND);
            put(IdentifierNotValid.class, HttpStatus.INTERNAL_SERVER_ERROR);
        }};
    }
}
