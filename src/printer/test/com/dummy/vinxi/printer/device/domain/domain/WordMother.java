package com.dummy.vinxi.printer.device.domain.domain;

public final class WordMother {
    public static String random() {
        return MotherCreator.random().lorem().word();
    }
}
