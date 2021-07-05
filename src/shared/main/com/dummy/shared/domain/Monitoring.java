package com.dummy.shared.domain;

public interface Monitoring {
    void incrementCounter(int times);

    void incrementGauge(int times);

    void decrementGauge(int times);

    void setGauge(int value);
}
