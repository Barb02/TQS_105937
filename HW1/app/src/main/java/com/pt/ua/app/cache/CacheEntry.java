package com.pt.ua.app.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEntry {
    private double value;
    private long expirationTime;

    private static final Logger log = LoggerFactory.getLogger(CacheEntry.class);

    public CacheEntry(double value, long ttl) {
        this.value = value;
        this.expirationTime = System.currentTimeMillis() + ttl * 1000L;
    }

    public double getValue() {
        return value;
    }

    public boolean isExpired() {
        if (System.currentTimeMillis() > expirationTime){
            log.info("Cache entry expired");
            return true;
        }
        return false;
    }
}
