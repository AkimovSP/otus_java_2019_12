package ru.otus.cachehw;

import org.slf4j.Logger;

public class HwListenerImpl implements HwListener {
    Logger logger;
    private String name;

    public HwListenerImpl(Logger logger, String name) {
        this.logger = logger;
        this.name = name;
    }

    @Override
    public void notify(Object key, Object value, String action) {
        logger.info("Listener " + this.name + ":     action - key:{}, value:{}, action: {}", key, value, action);
    }
}
