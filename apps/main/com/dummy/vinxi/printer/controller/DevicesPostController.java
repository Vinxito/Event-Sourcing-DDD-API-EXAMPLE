package com.dummy.vinxi.printer.controller;


import com.dummy.vinxi.printer.device.application.change_status.ChangeStatusDeviceCommand;
import com.dummy.vinxi.printer.device.domain.DevicesNotExist;
import com.dummy.vinxi.shared.domain.bus.command.CommandBus;
import com.dummy.vinxi.shared.infrastructure.spring.CommandApiController;
import com.dummy.vinxi.shared.domain.DomainError;
import com.dummy.vinxi.shared.domain.IdentifierNotValid;
import com.dummy.vinxi.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/devices/")
public class DevicesPostController extends CommandApiController {

    public DevicesPostController(CommandBus commandBus) {
        super(commandBus);
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<HttpStatus> updateDevice(@PathVariable String id, @RequestBody PostRequest postRequest)
            throws CommandHandlerExecutionError {
        dispatch(new ChangeStatusDeviceCommand(id, postRequest.status()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<Class<? extends DomainError>, HttpStatus>() {{
            put(DevicesNotExist.class, HttpStatus.NOT_FOUND);
            put(IdentifierNotValid.class, HttpStatus.INTERNAL_SERVER_ERROR);
        }};
    }
}

final class PostRequest {
    private String status;

    public String status() {
        return status;
    }
}



