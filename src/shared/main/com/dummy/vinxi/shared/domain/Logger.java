package com.dummy.vinxi.shared.domain;

import java.io.Serializable;
import java.util.HashMap;

public interface Logger {

    void warning(String message, Exception exception);

    void warning(String message, HashMap<String, Serializable> context, Exception exception);

    void warning(Exception exception);

    void error(String message, Exception exception);

    void error(String message, HashMap<String, Serializable> context, Exception exception);

    void error(Exception exception);
}
