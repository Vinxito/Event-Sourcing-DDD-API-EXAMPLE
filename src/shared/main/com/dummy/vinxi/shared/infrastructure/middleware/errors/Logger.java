package com.dummy.vinxi.shared.infrastructure.middleware.errors;

import com.dummy.vinxi.shared.infrastructure.spring.Service;
import org.apache.logging.log4j.LogManager;

import java.io.Serializable;
import java.util.HashMap;

@Service
public class Logger implements com.dummy.vinxi.shared.domain.Logger {

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(getClass());

    @Override
    public void warning(Exception exception) {
        logger.warn(exception);
    }

    @Override
    public void warning(String message, Exception exception) {
        logger.warn(message, exception);
    }

    @Override
    public void warning(String message, HashMap<String, Serializable> context, Exception exception) {
        logger.warn(message, exception);
    }

    @Override
    public void error(Exception exception) {
        logger.error(exception);
    }

    @Override
    public void error(String message, Exception exception) {
        logger.error(message, exception);
    }

    @Override
    public void error(String message, HashMap<String, Serializable> context, Exception exception) {
        logger.error(message, exception);
    }
}
