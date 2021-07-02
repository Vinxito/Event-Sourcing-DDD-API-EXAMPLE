package com.dummy.vinxi.shared.infrastructure.spring;

import com.dummy.vinxi.shared.domain.DomainError;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public abstract class ApiController {

    public abstract HashMap<Class<? extends DomainError>, HttpStatus> errorMapping();

}
