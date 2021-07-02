package com.dummy.vinxi.shared.infrastructure.middleware.analitics;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class Monitoring implements com.dummy.vinxi.shared.domain.Monitoring {

    static final Counter counter = Counter.build().name("event_counter")
            .labelNames("event").help("Counter of event.").register();

    static final Gauge gauge = Gauge.build().name("event_gauge")
            .help("Gauge of event.").labelNames("method", "code").register();

    @Override
    public void incrementCounter(int times) {
        counter.labels().inc(times);
    }

    @Override
    public void incrementGauge(int times) {
        gauge.labels().inc(times);
    }

    @Override
    public void decrementGauge(int times) {
        gauge.labels().inc(times);
    }

    @Override
    public void setGauge(int value) {
        gauge.labels("get", "200").set(Math.random());
    }
}
