package com.dummy.device.controller.health_check;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public final class HealthCheckGetController {

    @GetMapping("/device/health-check")
    public HashMap<String, String> index() {

        HashMap<String, String> status = new HashMap<>();
        status.put("application", "aggregate");
        status.put("status", "ok");

        return status;
    }
}
