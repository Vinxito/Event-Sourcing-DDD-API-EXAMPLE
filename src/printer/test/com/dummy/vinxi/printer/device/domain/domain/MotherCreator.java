package com.dummy.vinxi.printer.device.domain.domain;

import com.github.javafaker.Faker;

public final class MotherCreator {
    private final static Faker faker = new Faker();

    public static Faker random() {
        return faker;
    }
}
