package com.dummy.vinxi.printer.device.domain.domain;

import java.util.Random;

public final class RandomElementPicker {

    @SafeVarargs
    public static <T> T from(T... elements) {
        Random rand = new Random();
        return elements[rand.nextInt(elements.length)];
    }
}
