package com.dummy.device.controller;

import com.dummy.printer.domain.DevicesNotExist;
import com.dummy.printer.application.create.CreateDeviceCommand;
import com.dummy.shared.domain.bus.command.CommandBus;
import com.dummy.shared.infrastructure.spring.CommandApiController;
import com.dummy.shared.domain.DomainError;
import com.dummy.shared.domain.IdentifierNotValid;
import com.dummy.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/devices/")
public class DevicesPutController extends CommandApiController {

    public DevicesPutController(CommandBus commandBus) {
        super(commandBus);
    }

    @PutMapping("/create/{id}")
    public ResponseEntity<HttpStatus> registerDevice(@PathVariable String id, @RequestBody PutRequest putRequest)
            throws CommandHandlerExecutionError {
        dispatch(new CreateDeviceCommand(id, putRequest.name(), putRequest.status()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<Class<? extends DomainError>, HttpStatus>() {{
            put(DevicesNotExist.class, HttpStatus.NOT_FOUND);
            put(IdentifierNotValid.class, HttpStatus.INTERNAL_SERVER_ERROR);
        }};
    }
}

final class PutRequest {
    private String name;
    private String status;

    public String name() {
        return name;
    }

    public String status() {
        return status;
    }
}

