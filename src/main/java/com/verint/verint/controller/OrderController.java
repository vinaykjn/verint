package com.verint.verint.controller;

import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class OrderController {
    private final MetricsEndpoint metricsEndpoint;

    public OrderController(MetricsEndpoint metricsEndpoint) {
        this.metricsEndpoint = metricsEndpoint;
    }

    @GetMapping("/metrics")
    public Object getMetrics() {
        return metricsEndpoint.metric("jvm.memory.used", null);
    }
}
