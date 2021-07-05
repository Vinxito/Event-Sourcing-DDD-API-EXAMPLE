package com.dummy.device.controller;

import com.dummy.printer.domain.DevicesNotExist;
import com.dummy.printer.application.remove.RemoveDeviceCommand;
import com.dummy.shared.domain.bus.command.CommandBus;
import com.dummy.shared.infrastructure.spring.CommandApiController;
import com.dummy.shared.domain.DomainError;
import com.dummy.shared.domain.IdentifierNotValid;
import com.dummy.shared.domain.bus.command.CommandHandlerExecutionError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/devices/")
public class DevicesDeleteController extends CommandApiController {

    public DevicesDeleteController(CommandBus commandBus) {
        super(commandBus);
    }
  
    @DeleteMapping("remove/{id}")
    public ResponseEntity<HttpStatus> cancelDevice(@PathVariable String id) throws CommandHandlerExecutionError {
        dispatch(new RemoveDeviceCommand(id));
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
